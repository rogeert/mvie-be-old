package com.rogeert.mviebe.services.role

import com.rogeert.mviebe.models.entities.Role
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.util.Response

interface RoleService {

    fun newRole(newRole:String):Response<Role>

    fun updateRole(role:String,newRole:String):Response<Role>

    fun deleteRole(role:String):Response<String>

    fun getRole(role:String):Response<Role>

    fun getAllRoles():Response<List<Role>>
}