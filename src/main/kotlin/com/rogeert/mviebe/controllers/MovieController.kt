package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.models.entities.Movie
import com.rogeert.mviebe.services.movie.MovieServiceImpl
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/movies")
class MovieController {

    @Value("\${db_movie_auth}")
    var token = ""

    @Value("\${movie_db_access_token}")
    var accessToken = ""

    @Autowired
    lateinit var movieServiceImpl: MovieServiceImpl

    @PostMapping()
    fun addMovie(@RequestParam("title") title:String, @RequestParam("media_id") mediaId:Long,@RequestParam("movie_db_id") movieDbId:Long):ResponseEntity<Response<Movie>>{

        val response = movieServiceImpl.addMovie(title,movieDbId,mediaId)

        return ResponseEntity(response,response.status!!)
    }


    @GetMapping("/thmdb/{id}")
    fun getByMovieDbId(@PathVariable("id") movieDbId:Long):ResponseEntity<Response<Movie>>{

        val response = movieServiceImpl.getByMovieDb(movieDbId)

        return ResponseEntity(response,response.status!!)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") movieId:Long, @RequestParam("title") title:String,@RequestParam("media_id") mediaId: Long):ResponseEntity<Response<Movie>>{

        val response = movieServiceImpl.update(movieId,mediaId,title)

        return ResponseEntity(response,response.status!!)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") movieId:Long):ResponseEntity<Response<String>>{
        val response = movieServiceImpl.deleteById(movieId)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/tokens")
    fun getAccessToken():ResponseEntity<MDBTokens>{

        val body = MDBTokens(accessToken,token)

        return ResponseEntity(body,HttpStatus.OK)
    }

}

data class MDBTokens(val accessToken:String,val token: String)