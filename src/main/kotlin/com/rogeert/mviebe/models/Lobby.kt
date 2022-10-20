package com.rogeert.mviebe.models

import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.util.Response
import org.springframework.http.codec.ServerSentEvent
import org.springframework.http.codec.ServerSentEventHttpMessageWriter
import reactor.core.publisher.Flux
import java.time.Duration

class Lobby {

    var code: String = "-"
    var usersCap = 8
    var users = ArrayList<User>()
    lateinit var emitter: Flux<Response<String>>

    fun addUser(user: User): Boolean{

        if(users.size == usersCap)
            return false

        users.add(user)
        return true
    }

    fun kickUser(username: String):Boolean{
        

        return users.removeIf { it.username == username }
    }

}