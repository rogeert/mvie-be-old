package com.rogeert.mviebe.websocket.config

import com.corundumstudio.socketio.SocketIOServer
import com.rogeert.mviebe.models.entities.Media
import com.rogeert.mviebe.repositories.MediaRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.nio.file.Files


@Component
@Slf4j
@RequiredArgsConstructor
class SocketIOCommandRunner(private val server: SocketIOServer,private val mediaRepository: MediaRepository): CommandLineRunner{


    override fun run(vararg args: String?) {
        server.start()
        populateMedias()
    }


    fun populateMedias(){

        Files.newDirectoryStream(ResourceUtils.getFile("classpath:content/videos").toPath()).use { stream ->
            for (path in stream) {
                if (!Files.isDirectory(path)) {

                    val fileName = path.fileName
                        .toString().substringBefore(".")

                    if(mediaRepository.findMediaByFileName(fileName) == null){
                        val media = Media()
                        media.fileName = fileName
                        media.size = Files.size(path)

                        mediaRepository.save(media)
                    }

                }
            }
        }


    }

}