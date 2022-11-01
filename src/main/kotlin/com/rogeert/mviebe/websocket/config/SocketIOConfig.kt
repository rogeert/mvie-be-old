package com.rogeert.mviebe.websocket.config

import com.corundumstudio.socketio.SocketIOServer
import com.rogeert.mviebe.security.TokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SocketIOConfig(val tokenProvider: TokenProvider) {


    @Value("\${socket-server.host}")
    private val host: String? = null

    @Value("\${socket-server.port}")
    private val port: Int? = null

    @Bean
    fun socketIOServer():SocketIOServer{
        val configuration = com.corundumstudio.socketio.Configuration()
        configuration.hostname = host
        configuration.port = port!!
        configuration.authorizationListener = SocketIOAuth(tokenProvider)
        return SocketIOServer(configuration)
    }


}