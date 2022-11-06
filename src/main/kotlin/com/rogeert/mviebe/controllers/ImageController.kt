package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.models.entities.Image
import com.rogeert.mviebe.services.image.ImageServiceImpl
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/images/")
class ImageController {

    @Autowired
    lateinit var imageServiceImpl: ImageServiceImpl


    @PostMapping()
    fun uploadImage(@RequestParam data: MultipartFile):ResponseEntity<Response<Image>>{
        val response = imageServiceImpl.saveImage(data)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping(value= ["/{id}"], produces = [MediaType.IMAGE_PNG_VALUE])
    fun getImage(@PathVariable id:Long):ByteArray{

        val response = imageServiceImpl.getImage(id)

        return response!!
    }

    @GetMapping("/all")
    fun getAllImages():ResponseEntity<Response<List<String>>>{
        val response = imageServiceImpl.getAllImages()

        return ResponseEntity(response,response.status!!)
    }

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id:Long):ResponseEntity<Response<String>>{
        val response = imageServiceImpl.deleteImage(id)

        return ResponseEntity(response,response.status!!)
    }

}