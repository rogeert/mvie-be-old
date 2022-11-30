package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.services.lobby.LobbyServiceImpl
import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.websocket.dto.LobbyDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/lobby")
class LobbyController(val lobbyServiceImpl: LobbyServiceImpl) {


    @PostMapping("/create")
    fun createLobby(principal: Principal):ResponseEntity<Response<String>>{
        val response = lobbyServiceImpl.createLobby(principal.name)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/join")
    fun joinLobby(principal: Principal,@RequestParam("code") code:String):ResponseEntity<Response<LobbyDto>>{
        val response = lobbyServiceImpl.joinLobby(code,principal.name)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/leave")
    fun leaveLobby(principal: Principal):ResponseEntity<Response<String>>{
        val response = lobbyServiceImpl.leaveLobby(principal.name)

        return ResponseEntity(response,response.status!!)
    }

}