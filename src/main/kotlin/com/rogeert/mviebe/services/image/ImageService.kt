package com.rogeert.mviebe.services.image

import com.rogeert.mviebe.models.entities.Image
import com.rogeert.mviebe.util.Response
import org.springframework.web.multipart.MultipartFile

interface ImageService {

    fun saveImage(data: MultipartFile):Response<Image>

    fun getImage(id:Long):ByteArray?

    fun getAllImages():Response<List<String>>

    fun deleteImage(id:Long):Response<String>
}