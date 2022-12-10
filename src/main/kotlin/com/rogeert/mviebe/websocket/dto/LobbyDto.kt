package com.rogeert.mviebe.websocket.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.rogeert.mviebe.models.entities.User

data class LobbyDto(

    @JsonProperty("mediaType")
    val mediaType:String,
    @JsonProperty("selectedMedia")
    val selectedMedia:Int,
    @JsonProperty("leader")
    val leader:String,
    @JsonProperty("code")
    val code:String,
    @JsonProperty("users")
    val users:List<User>

)