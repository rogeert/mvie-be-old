package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.Media
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MediaRepository: JpaRepository<Media,Long> {

    fun findMediaByMovieId(movieId: Long):Media?

}