package com.rogeert.mviebe.services.media

import com.rogeert.mviebe.models.entities.Media
import com.rogeert.mviebe.util.Response


interface MediaService {

    fun getAllMedia(): Response<List<Media>>

    fun getFiles():Response<List<String>>

}