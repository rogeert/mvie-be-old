package com.rogeert.mviebe.services.media

import com.rogeert.mviebe.models.entities.Media
import com.rogeert.mviebe.repositories.MediaRepository
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.nio.file.Files


@Service
class MediaServiceImpl:MediaService {

    @Autowired
    lateinit var mediaRepository: MediaRepository


    override fun getAllMedia(): Response<List<Media>> {

        val response = Response<List<Media>>()

        response.data = mediaRepository.findAll()
        response.status = HttpStatus.OK
        response.success = true
        response.messages.add("All medias.")

        return response
    }

    override fun getFiles(): Response<List<String>> {

        val response = Response<List<String>>()

        val fileSet: MutableSet<String> = HashSet()
        Files.newDirectoryStream(ResourceUtils.getFile("classpath:content/videos").toPath()).use { stream ->
            for (path in stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(
                        path.fileName
                            .toString().substringBefore(".")
                    )
                }
            }
        }

        response.messages.add("All files")
        response.data = fileSet.toList()
        response.status = HttpStatus.OK
        response.success = true

        return response
    }
}