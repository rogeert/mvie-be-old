package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User,Long> {


    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

    @Query(value = "SELECT u FROM User u")
    fun paginated(pageable: Pageable):List<User>

}