package com.rogeert.mviebe.services.lobby

import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.websocket.dto.LobbyDto

interface LobbyService {

    fun createLobby():Response<LobbyDto>

    fun joinLobby(code:String,username:String):Response<LobbyDto>

    fun leaveLobby(code:String,username:String):Response<LobbyDto>

}