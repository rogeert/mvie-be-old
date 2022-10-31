package com.rogeert.mviebe.services.lobby

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.rogeert.mviebe.models.Lobby
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.repositories.UserRepository
import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.websocket.MessageType
import com.rogeert.mviebe.websocket.dto.LobbyDto
import com.rogeert.mviebe.websocket.dto.LobbyEvent
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*


@Service
@Component
class LobbyServiceImpl(private final val server: SocketIOServer, private val userRepository: UserRepository): LobbyService {

    private final val logger = LoggerFactory.getLogger(LobbyServiceImpl::class.java)
    private final val lobbies = hashMapOf<String, Lobby>()
    private final val usersSocket = hashMapOf<String, User>()
    init {
        server.addConnectListener(onConnected())
        server.addDisconnectListener(onDisconnected())
        server.addEventListener("send_event",LobbyEvent::class.java,onLobbyEvent())
    }


    private fun onConnected(): ConnectListener = ConnectListener { client: SocketIOClient ->

        val username: String = client.handshakeData.getSingleUrlParam("username")

        val user = userRepository.findByUsername(username)

        if(user.isEmpty){
            logger.info("User $username could not connect, not found in the database.")
            client.disconnect()
        }else{
            user.get().socket = client
            usersSocket[username] = user.get()

            logger.info("User $username : ${client.remoteAddress} has connected.")
        }
    }

    private fun onLobbyEvent(): DataListener<LobbyEvent> {

        return DataListener<LobbyEvent>{ senderClient: SocketIOClient?, data: LobbyEvent, ackSender: AckRequest? ->

            lobbies[data.code]?.sendMessage(data.triggeredBy,data.content)
        }
    }

    private fun onDisconnected(): DisconnectListener {
        return DisconnectListener { client: SocketIOClient ->

            val pair = usersSocket.filterValues { it.socket!!.sessionId == client.sessionId }.toList()[0]

            pair.second.username?.let { lobbies[pair.second.partyCode]?.leave(it) }
            usersSocket.remove(pair.first)

            logger.info("User ${client.remoteAddress} has disconnected.")
        }
    }

    override fun createLobby(username:String): Response<String> {
        val response = Response<String>()
        val lobbyCode = generateLobbyCode(6)

        if(usersSocket[username] != null){

            val user = usersSocket[username]!!

            user.partyCode?.let {
                if(it.length > 2){
                    response.messages.add("User is already in a party {${user.partyCode}}")
                    response.success = false
                    response.status = HttpStatus.BAD_REQUEST

                    return response
                }
            }

            val lobby = Lobby(lobbyCode,8,user)
            lobby.join(usersSocket[username]!!,username)
            lobbies[lobbyCode] = lobby

            response.success = true
            response.status = HttpStatus.OK
            response.messages.add("Lobby created successfully.")
            response.data = lobbyCode
            return response
        }

        response.messages.add("Socket connection is not established.")
        response.success = false
        response.status = HttpStatus.BAD_REQUEST

        return response
    }

    override fun joinLobby(code: String, username: String): Response<LobbyDto> {
        val response = Response<LobbyDto>()

        val lobbyToJoin = lobbies[code]

        response.status = HttpStatus.BAD_REQUEST
        response.success = false

        if(lobbyToJoin != null && usersSocket[username] != null){

            return if(lobbyToJoin.join(usersSocket[username]!!,username)){
                val lobbyDto = LobbyDto(code,MessageType.SERVER,username,lobbyToJoin.getUsers())

                response.messages.add("$username joined {$code} party.")
                response.success = true
                response.status = HttpStatus.OK
                response.data = lobbyDto
                response
            }else{
                response.messages.add("User is already in this lobby.")
                response
            }
        }

        response.messages.add("Lobby or user not found.")
        return response
    }

    override fun leaveLobby(code: String, username: String): Response<String> {
        val response = Response<String>()

        val lobbyToJoin = lobbies[code]

        response.status = HttpStatus.BAD_REQUEST
        response.success = false

        if(lobbyToJoin != null && usersSocket[username] != null){

            return if(lobbyToJoin.leave(username)){

                if(lobbyToJoin.getUsers().isEmpty()){
                    lobbies.remove(lobbyToJoin.code)
                }

                response.messages.add("$username left {$code} party.")
                response.success = true
                response.status = HttpStatus.OK
                response.data = "Success"
                response
            }else{
                response.messages.add("User is not in this lobby.")
                response
            }
        }

        response.messages.add("Lobby or user not found.")
        return response

    }

    fun generateLobbyCode(length:Int):String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val sb = StringBuilder()

        val random = Random()

        do{

            for (i in 0 until length) {

                val index: Int = random.nextInt(alphabet.length)

                val randomChar = alphabet[index]
                sb.append(randomChar)
            }

        }while(lobbies[sb.toString()] != null)

        return sb.toString()
    }


}