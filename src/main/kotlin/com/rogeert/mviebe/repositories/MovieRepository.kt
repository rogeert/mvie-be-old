package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MovieRepository:JpaRepository<Movie,Long> {

    fun findMovieByMdbId(id:Long):Movie?

    @Modifying
    @Query("DELETE FROM Movie m WHERE m.mdbId = ?1")
    fun deleteMovieByMdbId(id:Long)

}