package com.rogeert.mviebe.repositories

import com.rogeert.mviebe.models.entities.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository: JpaRepository<Role,Long> {

    fun findRoleByName(name: String):Role?

}