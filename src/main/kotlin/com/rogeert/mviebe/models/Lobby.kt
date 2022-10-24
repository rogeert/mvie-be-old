package com.rogeert.mviebe.models

import com.corundumstudio.socketio.SocketIOClient
import com.rogeert.mviebe.models.entities.User
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Slf4j
class Lobby(var code: String,var usersCap:Int,var partyLeader:User) {

    var logger: Logger = LoggerFactory.getLogger(Lobby::class.java)

    private var users: HashMap<SocketIOClient, User> = hashMapOf()


    fun join(client: SocketIOClient,user:User){
        users[client] = user
        users.map {
            it.key.sendEvent("join_party",it.value)
        }
        logger.info("${user.username} has joined $code party.")
    }

    fun leave(client: SocketIOClient,user:User){
        users.map {
            it.key.sendEvent("leave_party",it.value)
        }
        users.remove(client)

        logger.info("${user.username} has left $code party.")
    }

}