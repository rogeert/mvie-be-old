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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

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

    @PutMapping("/{username}")
    fun updateUser(@RequestBody newUser: User,@PathVariable username: String):ResponseEntity<Response<User>>{

        val response = userService.updateUser(username,newUser)

        return ResponseEntity(response,response.status!!)
    }

    @PostMapping("/role/{username}/{roleId}")
    fun addRole(@PathVariable("username") username: String,@PathVariable("roleId") roleId: Long):ResponseEntity<Response<User>>{


        val response = userService.addRole(username,roleId)

        return ResponseEntity(response,response.status!!)
    }

    @DeleteMapping("/role/{username}/{roleId}")
    fun removeRole(@PathVariable("username") username: String,@PathVariable("roleId") roleId: Long):ResponseEntity<Response<User>>{


        val response = userService.removeRole(username,roleId)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("")
    fun getAllUsers(@RequestParam userPerPage: Int, @RequestParam page: Int):ResponseEntity<Response<Page<User>>>{

        val response = userService.getAllUsers(userPerPage,page)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String):ResponseEntity<Response<User>>{

        val response = userService.getUserByUsername(username)

        return ResponseEntity(response,response.status!!)
    }

    //TODO to test once the security is implemented
    @GetMapping("/profile")
    fun getProfile(principal: Principal):ResponseEntity<Response<User>>{

        val response = userService.getUserByUsername(principal.name)

        return ResponseEntity(response,response.status!!)
    }

    //TODO to test once the security is implemented
    @PutMapping("/profile")
    fun updateProfile(principal: Principal,@RequestBody newUser: User):ResponseEntity<Response<User>>{

        val response = userService.updateUser(principal.name,newUser)

        return ResponseEntity(response,response.status!!)
    }



    @DeleteMapping("/{username}")
    fun deleteUser(@PathVariable username: String):ResponseEntity<Response<String>>{

        val response = userService.deleteUserByUsername(username)

        return ResponseEntity(response,response.status!!)
    }
}