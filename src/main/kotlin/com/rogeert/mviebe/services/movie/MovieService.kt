package com.rogeert.mviebe.services.movie

import com.rogeert.mviebe.models.entities.Movie
import com.rogeert.mviebe.util.Response

interface MovieService {

    fun addMovie(title: String,dbMovieId:Long,mediaId:Long):Response<Movie>

    fun getByMovieDb(id:Long):Response<Movie>

    fun deleteById(id:Long):Response<String>

    fun update(movieId:Long, mediaId:Long,title:String):Response<Movie>
}