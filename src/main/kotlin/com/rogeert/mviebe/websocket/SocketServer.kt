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
import java.util.*

@Slf4j
class SocketServer(private final val server: SocketIOServer) {

    @Autowired
    lateinit var userRepository: UserRepository

    private final val logger = LoggerFactory.getLogger(SocketServer::class.java)
    private final val lobbies = hashMapOf<String,Lobby>()

    init {

        server.addEventListener("create_lobby",LobbyDto::class.java,onCreateLobby())
    }


    private fun onCreateLobby():DataListener<LobbyDto>{

        return DataListener<LobbyDto>{ senderClient: SocketIOClient?, data: LobbyDto, ackSender: AckRequest? ->

            val user = userRepository.findByUsername(data.username).get()
            val lobby = Lobby("",8,user)
            //lobby.join(senderClient!!,user)
        }
    }

}