package com.rogeert.mviebe.services.user

import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.repositories.UserRepository
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    lateinit var userRepository: UserRepository


    override fun getUserByUsername(username: String): Response<User> {

        val response: Response<User> = Response()

        val user = userRepository.findByUsername(username)

        user?.let {
            response.data = it
            response.messages.add("User found.")

            return response
        }

        response.status = HttpStatus.BAD_REQUEST
        response.success = false
        response.messages.add("No user found with {$username}.")

        return response
    }

    override fun updateUser(id: Long, newUser: User): Response<User> {
        val response: Response<User> = Response()

        val user = userRepository.findById(id)

        if(user.isPresent){
            if(newUser.email.isNullOrEmpty() || newUser.username.isNullOrEmpty() || newUser.name.isNullOrEmpty() || newUser.surname.isNullOrEmpty()){

                response.messages.add("Missing values!")
                response.status = HttpStatus.BAD_REQUEST
                response.success = false

                return response
            }

            user.get().username = newUser.username
            user.get().name = newUser.name
            user.get().surname = newUser.surname

            response.data = userRepository.save(user.get())
            response.messages.add("User was updated successfully.")
        }

        response.status = HttpStatus.BAD_REQUEST
        response.success = false

        return response
    }

    override fun deleteUserById(userId: Long): Response<String> {

        val response: Response<String> = Response()

        val user = userRepository.findById(userId)

        if(user.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("No user was found with this id {$userId}.")

            return response
        }

        userRepository.delete(user.get())

        response.messages.add("${user.get().username} was deleted successfully.")

        return response

    }

    override fun newUser(newUser: User): Response<User> {

        val response: Response<User> = Response()




    }




}