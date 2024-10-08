package com.rogeert.mviebe.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StreamingService {

    final val FORMAT = "classpath:content/videos/%s.mp4"
    @Autowired
    lateinit var resourceLoader : ResourceLoader;

    fun getVideo(videoId:String): Mono<Resource>{

         return Mono.fromSupplier {
            resourceLoader.getResource(String.format(FORMAT, videoId))
        }

    }

}