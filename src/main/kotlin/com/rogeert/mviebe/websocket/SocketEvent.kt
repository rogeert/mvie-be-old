package com.rogeert.mviebe.websocket

enum class SocketEvent {
    JOIN_LOBBY {},
    LEAVE_LOBBY {},
    CREATE_LOBBY {},
    PRIVATE_MESSAGE {},
    LOBBY_MESSAGE{};
}