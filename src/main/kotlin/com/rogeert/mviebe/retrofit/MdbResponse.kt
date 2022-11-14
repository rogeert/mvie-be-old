package com.rogeert.mviebe.retrofit

import com.google.gson.annotations.SerializedName

data class MdbResponse (

    @SerializedName("success"        ) var success       : Boolean? = null,
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("status_message" ) var statusMessage : String?  = null
        )