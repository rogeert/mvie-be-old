package com.rogeert.mviebe.services.user

import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.util.Response

interface UserService {

    fun getUserByUsername(username: String):Response<User>
    fun updateUser(id: Long, newUser:User):Response<User>
    fun deleteUserById(userId: Long):Response<String>
    fun newUser(newUser: User):Response<User>

}