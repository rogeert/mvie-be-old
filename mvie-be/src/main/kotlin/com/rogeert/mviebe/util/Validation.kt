package com.rogeert.mviebe.util

import com.rogeert.mviebe.models.entities.User
import java.util.regex.Pattern

class Validation {

    fun validateEmail(email: String):Boolean{
        return Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches()
    }

    fun validateUserBody(password: Boolean = false,body: User):Boolean{
        if(body.username.isNullOrEmpty() || body.email.isNullOrEmpty() || body.name.isNullOrEmpty() || body.surname.isNullOrEmpty()){
            if(password){
                if(body.password != null){
                    if(body.password!!.length < 8){
                        return false
                    }
                }
            }
        }

        return false
    }


}