package com.rogeert.mviebe.websocket

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.rogeert.mviebe.models.Lobby
import com.rogeert.mviebe.repositories.UserRepository
import com.rogeert.mviebe.websocket.dto.LobbyDto
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Slf4j
@Component
class SocketServer(private final val server: SocketIOServer) {

    @Autowired
    lateinit var userRepository: UserRepository

    private final val logger = LoggerFactory.getLogger(SocketServer::class.java)
    private final val lobbies = hashMapOf<String,Lobby>()

    init {
        server.addConnectListener(onConnected())
        server.addDisconnectListener(onDisconnected())
        server.addEventListener("create_lobby",LobbyDto::class.java,onCreateLobby())
    }


    private fun onCreateLobby():DataListener<LobbyDto>{

        return DataListener<LobbyDto>{ senderClient: SocketIOClient?, data: LobbyDto, ackSender: AckRequest? ->

            var code = generateLobbyCode(6)
            while(lobbies[code] != null){
                code = generateLobbyCode(6)
            }
            val user = userRepository.findByUsername(data.username).get()
            val lobby = Lobby(code,8,user)
            lobby.join(senderClient!!,user)
            lobbies[code] = lobby
        }
    }


    private fun onConnected(): ConnectListener = ConnectListener { client: SocketIOClient ->

        logger.info("User ${client.remoteAddress} has connected.")


        }

    private fun onDisconnected(): DisconnectListener {
        return DisconnectListener { client: SocketIOClient ->
            logger.info("User ${client.remoteAddress} has disconnected.")
        }
    }

    fun generateLobbyCode(length:Int):String{
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val sb = StringBuilder()

        val random = Random()

        for (i in 0 until length) {

            val index: Int = random.nextInt(alphabet.length)

            val randomChar = alphabet[index]
            sb.append(randomChar)
        }

        return sb.toString()
    }

}