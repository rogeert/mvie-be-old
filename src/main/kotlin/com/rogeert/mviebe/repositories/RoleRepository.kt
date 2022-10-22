package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.Role
import com.rogeert.mviebe.models.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoleRepository: JpaRepository<Role,Long> {

    fun findRoleByName(name: String):Optional<Role>

}