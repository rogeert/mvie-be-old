package com.rogeert.mviebe.websocket

import org.slf4j.LoggerFactory
import org.springframework.http.server.ServerHttpRequest
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal

class UserHandShakeHandler: DefaultHandshakeHandler() {

    var LOG = LoggerFactory.getLogger(UserHandShakeHandler::class.java)


    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Principal? {
        return super.determineUser(request, wsHandler, attributes)
    }

}