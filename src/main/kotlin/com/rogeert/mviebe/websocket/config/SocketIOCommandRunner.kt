package com.rogeert.mviebe.websocket.config

import com.corundumstudio.socketio.SocketIOServer
import com.rogeert.mviebe.models.entities.Media
import com.rogeert.mviebe.repositories.MediaRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.File
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

        Files.newDirectoryStream(ResourceUtils.getFile(System.getProperty("user.dir") + "/videos").toPath()).use { stream ->
            for (path in stream) {
                if (!Files.isDirectory(path)) {

                    val file = File(path.toUri())

                    val fileName = path.fileName
                        .toString().substringBefore(".")

                    val renamedFile = fileName.replace(" ","_")

                    if(fileName.contains(" ")){
                        val newFile = File(path.toUri().path.substringBeforeLast("/") + "/" + renamedFile + ".mp4")

                        file.renameTo(newFile)
                    }

                    if(mediaRepository.findMediaByFileName(renamedFile) == null){
                        val media = Media()
                        media.fileName = fileName.replace(" ","_")
                        media.size = Files.size(path)

                        mediaRepository.save(media)
                    }

                }
            }
        }


    }

}