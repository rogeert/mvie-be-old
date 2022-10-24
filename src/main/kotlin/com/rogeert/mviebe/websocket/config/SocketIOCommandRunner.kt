package com.rogeert.mviebe.websocket.config

import com.corundumstudio.socketio.SocketIOServer
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
@Slf4j
@RequiredArgsConstructor
class SocketIOCommandRunner(private val server: SocketIOServer): CommandLineRunner{



    override fun run(vararg args: String?) {
        server.start()
    }


}