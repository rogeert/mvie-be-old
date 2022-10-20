package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.services.user.UserServiceImpl
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.rogeert.mviebe.models.entities.User;
import com.rogeert.mviebe.util.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/users/")
class UserController {


    @Autowired
    final lateinit var userService: UserServiceImpl


    @PostMapping("")
    fun newUser(@RequestBody user: User):ResponseEntity<Response<User>>{

        val response = userService.newUser(user)


        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("")
    fun getAllUsers(@RequestParam userPerPage: Int, @RequestParam page: Int):ResponseEntity<Response<Page<User>>>{

        val response = userService.getAllUsers(userPerPage,page)


        return ResponseEntity(response,response.status!!)
    }
}