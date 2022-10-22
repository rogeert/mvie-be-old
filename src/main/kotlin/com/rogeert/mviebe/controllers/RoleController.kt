package com.rogeert.mviebe.controllers

import com.rogeert.mviebe.models.entities.Role
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.services.role.RoleServiceImpl
import com.rogeert.mviebe.util.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/roles/")
class RoleController {

    @Autowired
    final lateinit var roleServiceImpl: RoleServiceImpl


    @GetMapping("/{role}")
    fun getRole(@PathVariable("role") role: String): ResponseEntity<Response<Role>> {

        val response = roleServiceImpl.getRole(role)

        return ResponseEntity(response,response.status!!)
    }

    @GetMapping("")
    fun getAllRoles(): ResponseEntity<Response<List<Role>>> {

        val response = roleServiceImpl.getAllRoles()

        return ResponseEntity(response,response.status!!)
    }

    @PostMapping("")
    fun newRole(@RequestParam("name") roleName:String): ResponseEntity<Response<Role>> {

        val response = roleServiceImpl.newRole(roleName)

        return ResponseEntity(response,response.status!!)
    }


    @PutMapping("/{role}")
    fun updateRole(@PathVariable("role") role: String,@RequestParam("newName") newRole:String): ResponseEntity<Response<Role>> {

        val response = roleServiceImpl.updateRole(role,newRole)

        return ResponseEntity(response,response.status!!)
    }

    @DeleteMapping("/{role}")
    fun deleteRole(@PathVariable("role") role: String,): ResponseEntity<Response<String>> {

        val response = roleServiceImpl.deleteRole(role)

        return ResponseEntity(response,response.status!!)
    }


}