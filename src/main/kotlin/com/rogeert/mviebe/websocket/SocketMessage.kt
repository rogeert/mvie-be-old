package com.rogeert.mviebe.websocket

import com.fasterxml.jackson.annotation.JsonProperty


data class SocketMessage(
    @JsonProperty("sender")
    val sender: String,
    @JsonProperty("content")
    val content: String,
    @JsonProperty("code")
    val code:String
    )