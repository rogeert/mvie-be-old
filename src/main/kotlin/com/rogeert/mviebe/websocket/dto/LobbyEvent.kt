package com.rogeert.mviebe.websocket.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.rogeert.mviebe.websocket.SocketEvent

data class LobbyEvent(

    @JsonProperty("info")
    val info:String,

    @JsonProperty("lobbyCode")
    val code:String,

    @JsonProperty("event")
    val event:SocketEvent,

    @JsonProperty("user")
    val triggeredBy:String,

    @JsonProperty("content")
    val content:String,
)
