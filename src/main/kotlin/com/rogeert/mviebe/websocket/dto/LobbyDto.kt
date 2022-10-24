package com.rogeert.mviebe.websocket.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.rogeert.mviebe.websocket.MessageType

data class LobbyDto(

    @JsonProperty("code")
    val code:String,
    @JsonProperty("type")
    val type:MessageType,
    @JsonProperty("username")
    val username:String

)