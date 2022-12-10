package com.rogeert.mviebe.services.lobby

import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.websocket.dto.LobbyDto

interface LobbyService {

    fun createLobby(username: String):Response<LobbyDto>

    fun joinLobby(code:String,username:String):Response<LobbyDto>

    fun leaveLobby(username:String):Response<String>

    fun setReady(username: String, value: Boolean, code: String):Response<LobbyDto>

}