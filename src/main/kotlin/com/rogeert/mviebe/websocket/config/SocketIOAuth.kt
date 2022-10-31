package com.rogeert.mviebe.websocket.config

import com.corundumstudio.socketio.AuthorizationListener
import com.corundumstudio.socketio.HandshakeData

class SocketIOAuth: AuthorizationListener{

    override fun isAuthorized(p0: HandshakeData?): Boolean {

        return true
    }
}