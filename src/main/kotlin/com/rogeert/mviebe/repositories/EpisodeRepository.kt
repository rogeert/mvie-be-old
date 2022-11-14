package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.Episode
import org.springframework.data.jpa.repository.JpaRepository

interface EpisodeRepository: JpaRepository<Episode,Long> {

    fun findEpisodeByMdbId(id:Long):Episode?

}