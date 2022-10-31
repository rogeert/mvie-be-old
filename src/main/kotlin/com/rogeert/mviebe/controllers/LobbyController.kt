package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.services.lobby.LobbyServiceImpl
import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.websocket.dto.LobbyDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/lobby")
class LobbyController(val lobbyServiceImpl: LobbyServiceImpl) {


    @PostMapping("/create")
    fun createLobby(@RequestParam("username") username:String):ResponseEntity<Response<String>>{
        val response = lobbyServiceImpl.createLobby(username)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/join")
    fun joinLobby(@RequestParam("username") username:String,@RequestParam("code") code:String):ResponseEntity<Response<LobbyDto>>{
        val response = lobbyServiceImpl.joinLobby(code,username)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/leave")
    fun leaveLobby(@RequestParam("username") username:String,@RequestParam("code") code:String):ResponseEntity<Response<String>>{
        val response = lobbyServiceImpl.leaveLobby(code,username)

        return ResponseEntity(response,response.status!!)
    }

}