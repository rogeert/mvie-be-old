package com.rogeert.mviebe.websocket.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LobbyEvent(

    @JsonProperty("username")
    val username:String,

    @JsonProperty("event")
    val event:LobbyEventEnum,

    @JsonProperty("lobby")
    val lobby: LobbyDto
)



enum class LobbyEventEnum{
    JOIN, LEAVE, MEDIA_SELECT;
}