package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User,Long> {


    fun findByUsername(username: String):User?

}