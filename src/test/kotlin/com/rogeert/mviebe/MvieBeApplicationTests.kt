package com.rogeert.mviebe

import com.rogeert.mviebe.models.entities.Role
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.services.user.UserServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MvieBeApplicationTests {

    @Autowired
    lateinit var userService: UserServiceImpl

    fun addUser() {

        val user = User()
        user.name = "Rogert"
        user.surname = "Rekaj"
        user.username = "rogeert"
        user.email = "rogert_rekaj@hotmail.it"
        user.password = "sldkjhgsldjgflsd"
        val response = userService.newUser(user)

        response.data
    }

    @Test
    fun newRole(){

        val response = userService.removeRole("rogeert",2)

        response.data
    }

}
