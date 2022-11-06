package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository:JpaRepository<Image, Long> {
}