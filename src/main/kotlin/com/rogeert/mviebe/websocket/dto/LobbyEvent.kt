package com.rogeert.mviebe.websocket.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LobbyEvent(

    @JsonProperty("username")
    val username:String,

    @JsonProperty("lobbyCode")
    val code:String,

    @JsonProperty("event")
    val event:LobbyEventEnum,

    @JsonProperty("mediaId")
    val mediaId:Int?
)



enum class LobbyEventEnum{
    JOIN, LEAVE, MEDIA_SELECT;
}