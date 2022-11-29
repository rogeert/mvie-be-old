package com.rogeert.mviebe.models

import com.corundumstudio.socketio.SocketIOClient
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.websocket.SocketEvent
import com.rogeert.mviebe.websocket.SocketMessage
import com.rogeert.mviebe.websocket.dto.LobbyEvent
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Slf4j
class Lobby(var mediaType: MediaType = MediaType.NONE, var code: String, var usersCap:Int, var partyLeader:User) {

    var logger: Logger = LoggerFactory.getLogger(Lobby::class.java)

    private var users: HashMap<String, User> = hashMapOf()

    private var selectedMediaId = -1

    fun setPartyLeader(username:String){
        partyLeader = users[username]!!
    }

    fun setSelectedMedia(newMediaId:Int){
        selectedMediaId = newMediaId
    }

    fun getSelectedMedia():Int{
        return selectedMediaId
    }

    fun join(client: User,username:String):Boolean{

        if(users[username] != null)
            return false

        users.map {
            it.value.socket?.sendEvent("party_event",LobbyEvent("New user joined.",code,SocketEvent.JOIN_LOBBY,username,""))
        }
        client.partyCode = code
        users[username] = client
        logger.info("$username has joined $code party.")

        return true
    }

    fun leave(username:String):Boolean{
        if(users[username] == null)
            return false

        users.map {
            it.value.socket?.sendEvent("party_event",LobbyEvent("User left.",code,SocketEvent.LEAVE_LOBBY,username,""))
        }
        users.remove(username)

        logger.info("$username has left $code party.")

        return true
    }

    fun disconnected(sessionId:String){

        users.values.toList().first { it.socket?.sessionId.toString() == sessionId }


    }

    fun sendMessage(username:String,content:String){
        users.map {
            it.value.socket?.sendEvent("party_message",SocketMessage(username,content,code))
        }
    }

    fun getUsers():List<User>{
        return users.values.toList()
    }

}