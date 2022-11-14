package com.rogeert.mviebe.services.movie

import com.rogeert.mviebe.models.entities.Movie
import com.rogeert.mviebe.repositories.MediaRepository
import com.rogeert.mviebe.repositories.MovieRepository
import com.rogeert.mviebe.retrofit.MdbDTO
import com.rogeert.mviebe.retrofit.MdbResponse
import com.rogeert.mviebe.retrofit.MovieDbClient
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service


@Service
class MovieServiceImpl:MovieService {

    @Autowired
    lateinit var movieRepository: MovieRepository

    @Autowired
    lateinit var mediaRepository: MediaRepository

    @Value("\${movie_db_access_token}")
    var accessToken = ""

    lateinit var mdbClient: MovieDbClient

    @Value("\${mvie-db-movies-list}")
    private val listId: Int ? = -1


    override fun addMovie(title: String,dbMovieId:Long,mediaId:Long): Response<Movie> {
        val response = Response<Movie>()

        val movie = movieRepository.findMovieByMdbId(dbMovieId)
        val media = mediaRepository.findById(mediaId)


        if(media.isEmpty){
            response.messages.add("Media {$mediaId} notFound.")
            response.status = HttpStatus.BAD_REQUEST
            response.success= false

            return response
        }


        movie?.let {
            response.messages.add("Movie {$dbMovieId} already exists.")
            response.status = HttpStatus.BAD_REQUEST
            response.success= false

            return response
        }

        mdbClient = MovieDbClient(accessToken)


        val res:retrofit2.Response<MdbResponse> = mdbClient.movieService.addMovie(listId!!, MdbDTO(dbMovieId.toInt())).execute()

        if(res.code() != 200 && res.code() != 201){

            response.messages.add("Couldn't add movie to The Movie DB.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            return response
        }

        val newMovie = Movie()
        newMovie.media = media.get()
        newMovie.title = title
        newMovie.mdbId = dbMovieId

        response.messages.add("Movie was added successfully.")
        response.status = HttpStatus.OK
        response.success = true
        response.data = movieRepository.save(newMovie)

        return response
    }

    override fun getByMovieDb(id: Long): Response<Movie> {
        val response = Response<Movie>()

        val movie = movieRepository.findMovieByMdbId(id)

        movie?.let {

            response.messages.add("Movie data found.")
            response.status = HttpStatus.OK
            response.success= true
            response.data = it

            return response

        }


        response.messages.add("No movie data found with this id {$id}.")
        response.status = HttpStatus.BAD_REQUEST
        response.success = false

        return response
    }

    override fun deleteById(id: Long): Response<String> {
        val response = Response<String>()


        val movie = movieRepository.findMovieByMdbId(id)

        if(movie != null){
            mdbClient = MovieDbClient(accessToken)


            val res:retrofit2.Response<MdbResponse> = mdbClient.movieService.deleteMovie(listId!!, MdbDTO(id.toInt())).execute()

            if(res.code() != 200){

                response.messages.add("Couldn't delete movie from The Movie DB.")
                response.status = HttpStatus.BAD_REQUEST
                response.success = false
                return response
            }

            movieRepository.deleteMovieByMdbId(id)

            response.messages.add("Movie {$id} deleted.")
            response.status = HttpStatus.OK
            response.success= true
            response.data = "Success."

            return response
        }


        response.messages.add("No movie data found with this id {$id}.")
        response.status = HttpStatus.BAD_REQUEST
        response.success = false

        return response
    }



    override fun update(movieId: Long, mediaId: Long,title: String): Response<Movie> {

        val response = Response<Movie>()

        val movie = movieRepository.findById(movieId)
        val media = mediaRepository.findById(mediaId)


        if(media.isEmpty){
            response.messages.add("Media {$mediaId} notFound.")
            response.status = HttpStatus.BAD_REQUEST
            response.success= false

            return response
        }


        if(movie.isEmpty){
            response.messages.add("Movie {$movieId} not found.")
            response.status = HttpStatus.BAD_REQUEST
            response.success= false

            return response
        }


        movie.get().media = media.get()
        movie.get().title = title
        response.messages.add("Media was changed successfully.")
        response.status = HttpStatus.OK
        response.success = true
        response.data = movieRepository.save(movie.get())

        return response

    }
}