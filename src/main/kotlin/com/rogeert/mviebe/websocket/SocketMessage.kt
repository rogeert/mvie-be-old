package com.rogeert.mviebe.websocket

data class SocketMessage<T>(
    val event: SocketEvent,
    val username: String,
    val messageType: MessageType,
    val data:T
    )