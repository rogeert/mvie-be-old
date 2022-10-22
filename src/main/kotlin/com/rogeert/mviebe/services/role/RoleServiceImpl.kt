package com.rogeert.mviebe.services.role

import com.rogeert.mviebe.models.entities.Role
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.util.Validation
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl: Validation(),RoleService {

    override fun newRole(newRole: String): Response<Role> {
        val response: Response<Role> = Response()

        val roleCheck = roleRepository.findRoleByName(newRole)

        if(roleCheck.isPresent){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("This role {$newRole} already exists.")
            return response
        }

        val role = Role()

        role.name = newRole.uppercase()

        response.status = HttpStatus.CREATED
        response.success = true
        response.messages.add("New role {$newRole} created successfully.")
        response.data = roleRepository.save(role)

        return response
    }

    override fun updateRole(role: String, newRole: String): Response<Role> {
        val response: Response<Role> = Response()

        val roleCheck = roleRepository.findRoleByName(newRole)

        if(roleCheck.isPresent){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("This role {$newRole} already exists.")
            return response
        }

        val roleToUpdate = roleRepository.findRoleByName(role)

        if(roleToUpdate.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("This role {$role} does not exist.")
            return response
        }

        roleToUpdate.get().name = newRole.uppercase()
        response.status = HttpStatus.OK
        response.success = true
        response.messages.add("Role updated successfully. {$role} -> {$newRole}.")
        response.data = roleRepository.save(roleToUpdate.get())

        return response
    }

    override fun deleteRole(role: String): Response<String> {
        val response: Response<String> = Response()

        val roleCheck = roleRepository.findRoleByName(role)

        if(roleCheck.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("This role {$role} does not exist.")
            return response
        }

        roleRepository.delete(roleCheck.get())

        response.status = HttpStatus.OK
        response.success = true
        response.messages.add("Role {$role} deleted successfully.")
        response.data = "OK"

        return response
    }

    override fun getRole(role: String): Response<Role> {
        val response: Response<Role> = Response()

        val roleCheck = roleRepository.findRoleByName(role)

        if(roleCheck.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("This role {$role} does not exist.")
            return response
        }

        response.status = HttpStatus.OK
        response.success = true
        response.messages.add("Role {$role} found.")
        response.data = roleCheck.get()

        return response
    }

    override fun getAllRoles(): Response<List<Role>> {

        val response: Response<List<Role>> = Response()

        response.status = HttpStatus.OK
        response.messages.add("All roles available.")
        response.success = true
        response.data = roleRepository.findAll()

        return response
    }
}