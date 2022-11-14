package com.rogeert.mviebe.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDbClient(private val accessToken:String) {

    private val baseUrl = "https://api.themoviedb.org/3/"



    private val retrofit: Retrofit
    final val movieService : MovieDbService
    private val okhttpClient: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(RetrofitAuthInterceptor(accessToken)).build()

    init {

        retrofit = Retrofit.Builder().client(okhttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()

        movieService = retrofit.create(MovieDbService::class.java)
    }

}

class RetrofitAuthInterceptor(private val token:String): Interceptor{



    override fun intercept(p0: Interceptor.Chain): Response {

        val response = p0.request().newBuilder().
        addHeader("Authorization","Bearer $token").build()



        return p0.proceed(response)
    }

}