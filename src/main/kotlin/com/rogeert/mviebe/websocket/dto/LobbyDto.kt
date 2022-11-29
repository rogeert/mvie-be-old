package com.rogeert.mviebe.websocket.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.rogeert.mviebe.models.MediaType
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.websocket.MessageType

data class LobbyDto(

    @JsonProperty("media_type")
    val mediaType:MediaType,
    @JsonProperty("selected_media")
    val selectedMedia:Int,
    @JsonProperty("leader")
    val leader:String,
    @JsonProperty("code")
    val code:String,
    @JsonProperty("users")
    val users:List<User>

)