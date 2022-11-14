package com.rogeert.mviebe.retrofit

import com.google.gson.annotations.SerializedName

data class MdbDTO(
    @SerializedName("media_id")
    val movieId:Int
)
