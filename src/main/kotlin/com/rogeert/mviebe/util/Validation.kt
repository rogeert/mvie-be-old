package com.rogeert.mviebe.util

import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.repositories.RoleRepository
import com.rogeert.mviebe.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.util.regex.Pattern

open class Validation {


    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    fun validateEmail(email: String?):Boolean{

        if(email.isNullOrBlank())
            return false

        return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()
    }

    fun validateUserBody(response: Response<User>, user:User,image:Boolean = true, name:Boolean = true, surname: Boolean = true, username: Boolean = true,
                         email: Boolean = true,password: Boolean = true,):Boolean{

        if(name){
            if(!validateString(str = user.name)){
                response.messages.add("Name must be at least 3 chars long.")
            }
        }

        //TODO check if url is valid
        if(image){
            if(user.image.isNullOrBlank()){
                response.messages.add("Image url is missing.")
            }
        }

        if(surname){
            if(!validateString(str = user.surname)){
                response.messages.add("Surname must be at least 3 chars long.")
            }
        }

        if(username){
            if(!validateString(str = user.username)){
                response.messages.add("Username must be at least 3 chars long.")
            }else if(userRepository.findByUsername(user.username!!).isPresent)
                response.messages.add("This username {${user.username}} is already in use.")
        }

        if(email){
            if(!validateEmail(email = user.email)){
                response.messages.add("Email us not valid.")
            }else if(userRepository.findByEmail(user.email!!).isPresent)
                response.messages.add("This email {${user.email}} is already in use.")

        }

        if(password){
            if(!validateString(str = user.password, minChar = 8
                )){
                response.messages.add("Password must be at least 8 chars long.")
            }
        }

        if(response.messages.isNotEmpty()){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return false
        }

        return true
    }

    private fun validateString(minChar: Int = 3,str: String?):Boolean{

        return (str != null) && (str.length >= minChar)

    }
}