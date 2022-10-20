package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.services.StreamingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TestController {

    @Autowired
    lateinit var streamingService: StreamingService

    @GetMapping(value=["video/{title}"], produces = ["video/mp4"])
    fun getVideo(@PathVariable title: String, @RequestHeader("Range") range: String) : Mono<Resource>{

        return streamingService.getVideo(title)
    }

}