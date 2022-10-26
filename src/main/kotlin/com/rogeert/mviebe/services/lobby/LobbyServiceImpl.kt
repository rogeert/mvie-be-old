package com.rogeert.mviebe.services.lobby

import com.corundumstudio.socketio.SocketIOServer
import com.rogeert.mviebe.models.Lobby
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class LobbyServiceImpl(private final val server: SocketIOServer): LobbyService {

    private final val logger = LoggerFactory.getLogger(LobbyServiceImpl::class.java)
    private final val lobbies = hashMapOf<String, Lobby>()
    init {

    }

}