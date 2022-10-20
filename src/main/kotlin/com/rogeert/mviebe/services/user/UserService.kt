package com.rogeert.mviebe.services.user

import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.util.Page
import com.rogeert.mviebe.util.Response

interface UserService {

    fun getUserByUsername(username: String):Response<User>
    fun updateUser(username: String, newUser:User):Response<User>
    fun deleteUserById(username: String):Response<String>
    fun newUser(newUser: User):Response<User>

    fun getAllUsers(usersPerPage:Int = 10,page: Int = 0):Response<Page<User>>
    fun addRole(username: String, roleId: Long):Response<User>
    fun removeRole(username: String, roleId: Long): Response<User>
    fun changePassword(username: String):Response<User>
    fun resetPassword(email: String): Response<User>
}