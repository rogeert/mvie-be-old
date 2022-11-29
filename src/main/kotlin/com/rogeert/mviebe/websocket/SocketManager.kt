package com.rogeert.mviebe.websocket

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import com.rogeert.mviebe.models.Lobby
import com.rogeert.mviebe.repositories.UserRepository
import com.rogeert.mviebe.websocket.dto.LobbyDto
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Slf4j
@Component
class SocketManager(private final val server: SocketIOServer) {

    @Autowired
    lateinit var userRepository: UserRepository

    private final val logger = LoggerFactory.getLogger(SocketManager::class.java)
    private final val lobbies = hashMapOf<String,Lobby>()

    init {

        //server.addEventListener("create_lobby",LobbyDto::class.java,onCreateLobby())
    }




}