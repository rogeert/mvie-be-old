package com.rogeert.mviebe.services.lobby

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.rogeert.mviebe.models.Lobby
import com.rogeert.mviebe.models.MediaType
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.repositories.UserRepository
import com.rogeert.mviebe.security.TokenProvider
import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.websocket.SocketMessage
import com.rogeert.mviebe.websocket.dto.LobbyDto
import com.rogeert.mviebe.websocket.dto.LobbyEvent
import com.rogeert.mviebe.websocket.dto.LobbyEventEnum
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*


@Service
@Component
class LobbyServiceImpl(private val server: SocketIOServer, private val userRepository: UserRepository,private val tokenProvider: TokenProvider): LobbyService {

    private final val logger = LoggerFactory.getLogger(LobbyServiceImpl::class.java)
    private final val lobbies = hashMapOf<String, Lobby>()
    private final val usersSocket = hashMapOf<String, User>()
    init {
        server.addConnectListener(onConnected())
        server.addDisconnectListener(onDisconnected())
        server.addEventListener("client_message",SocketMessage::class.java,onClientMessage())
        server.addEventListener("party_event",LobbyEvent::class.java,onPartyEvent())
    }

    private fun onPartyEvent(): DataListener<LobbyEvent> {

        return DataListener<LobbyEvent>{ senderClient: SocketIOClient?, data: LobbyEvent, ackSender: AckRequest? ->

            when(data.event){
                LobbyEventEnum.MEDIA_SELECT ->{
                    //TODO
                }
            }

        }
    }

    private fun onConnected(): ConnectListener = ConnectListener { client: SocketIOClient ->

        val username: String = tokenProvider.getAuthentication(client.handshakeData.httpHeaders["Authorization"])!!.name

        val user = userRepository.findByUsername(username)

        if(user.isEmpty){
            logger.info("User $username could not connect, not found in the database.")
            client.disconnect()
        }else{
            user.get().socket = client
            usersSocket[username] = user.get()

            logger.info("User $username : ${client.remoteAddress} just connected.")
        }
    }

    private fun onClientMessage(): DataListener<SocketMessage> {

        return DataListener<SocketMessage>{ senderClient: SocketIOClient?, data: SocketMessage, ackSender: AckRequest? ->

            senderClient?.let {
                usersSocket.values.stream().filter { user-> user.socket!!.sessionId.toString() == senderClient.sessionId.toString() }.toList()[0]?.let {
                    lobbies[data.code]?.sendMessage(it.username!!,data.content)
                }

            }


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

            val lobbyCode = generateLobbyCode(6)
            val lobby = Lobby(MediaType.NONE,lobbyCode,8,user)
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
                val lobbyDto = LobbyDto(lobbyToJoin.mediaType,lobbyToJoin.getSelectedMedia(),lobbyToJoin.partyLeader.username!!,lobbyToJoin.code,lobbyToJoin.getUsers())

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

    override fun leaveLobby( username: String): Response<String> {
        val response = Response<String>()

        val user = usersSocket[username]

        response.status = HttpStatus.BAD_REQUEST
        response.success = false

       user?.partyCode?.let {
           val code = it
           val lobbyToJoin = lobbies[it]
           lobbyToJoin?.let {lobby->
               return if(lobby.leave(username)){

                   if(lobby.getUsers().isEmpty()){

                       lobbies.remove(lobby.code)
                   }
                   usersSocket[username]?.partyCode = null

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