package com.rogeert.mviebe.websocket.config

import com.corundumstudio.socketio.AuthorizationListener
import com.corundumstudio.socketio.HandshakeData
import com.rogeert.mviebe.security.AppAuthenticationManager
import com.rogeert.mviebe.security.TokenProvider

class SocketIOAuth(private val tokenProvider: TokenProvider): AuthorizationListener{

    override fun isAuthorized(p0: HandshakeData?): Boolean {

        val token = p0!!.httpHeaders["Authorization"]

        if(token.isNullOrEmpty())
            return false

        val authentication = tokenProvider.getAuthentication(token)

        var authenticated = false

        authentication?.let {
            authenticated = it.isAuthenticated
            it.name
        }

        return authenticated
    }
}