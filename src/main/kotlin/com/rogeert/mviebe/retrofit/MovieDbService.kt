package com.rogeert.mviebe.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface MovieDbService {

    @POST("list/{listId}/add_item")
    fun addMovie(@Path("listId") listId:Int, @Body body:MdbDTO):Call<MdbResponse>

    @POST("list/{listId}/remove_item")
    fun deleteMovie(@Path("listId") listId:Int, @Body body:MdbDTO):Call<MdbResponse>
}