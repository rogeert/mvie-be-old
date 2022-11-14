package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.models.entities.Media
import com.rogeert.mviebe.services.media.MediaServiceImpl
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/medias/")
class MediaController {

    @Autowired
    lateinit var mediaServiceImpl: MediaServiceImpl

    @GetMapping("/all")
    fun getAllMedias():ResponseEntity<Response<List<Media>>>{

        val response = mediaServiceImpl.getAllMedia()

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/all-files")
    fun getAllFiles():ResponseEntity<Response<List<String>>>{

        val response = mediaServiceImpl.getFiles()

        return ResponseEntity(response,response.status!!)
    }
}