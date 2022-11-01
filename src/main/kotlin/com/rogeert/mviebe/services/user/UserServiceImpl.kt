package com.rogeert.mviebe.services.user

import com.rogeert.mviebe.dtos.TokenDto
import com.rogeert.mviebe.models.entities.User
import com.rogeert.mviebe.security.TokenProvider
import com.rogeert.mviebe.util.Page
import com.rogeert.mviebe.util.Response
import com.rogeert.mviebe.util.Validation
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.awt.print.Pageable

@Component
class UserServiceImpl(val bCryptPasswordEncoder: BCryptPasswordEncoder,val tokenProvider: TokenProvider): Validation(), UserService {


    override fun getUserByUsername(username: String): Response<User> {

        val response: Response<User> = Response()

        val user = userRepository.findByUsername(username)

        // check if user with this username exists and return it
        if(user.isPresent){
            response.data = user.get()
            response.messages.add("User found.")

            return response
        }

        response.status = HttpStatus.BAD_REQUEST
        response.success = false
        response.messages.add("No user found with {$username}.")

        return response
    }

    override fun updateUser(username: String, newUser: User): Response<User> {
        val response: Response<User> = Response()

        val user = userRepository.findByUsername(username)

        //check if this user exist in the database
        if(user.isPresent){

            // check if body is valid and return BAD_REQUEST in case it's not
            if(!validateUserBody(response,newUser, password = false))
                return response

            if(newUser.email!! != user.get().email){
                if(userRepository.findByEmail(newUser.email!!).isPresent){
                    response.messages.add("This email {${newUser.email}} is already in use!")
                    response.status = HttpStatus.BAD_REQUEST
                    response.success = false
                    return response
                }
            }

            if(newUser.username!! != user.get().username){
                if(userRepository.findByUsername(newUser.username!!).isPresent){
                    response.messages.add("This username {${newUser.username}} is already in use!")
                    response.status = HttpStatus.BAD_REQUEST
                    response.success = false
                    return response
                }
            }

            // update and save user changes
            user.get().username = newUser.username
            user.get().email = newUser.email
            user.get().name = newUser.name
            user.get().surname = newUser.surname

            response.data = userRepository.save(user.get())
            response.messages.add("User was updated successfully.")
            response.status = HttpStatus.OK
            return response
        }

        response.messages.add("User not found!")
        response.status = HttpStatus.BAD_REQUEST
        response.success = false

        return response
    }

    override fun deleteUserByUsername(username: String): Response<String> {

        val response: Response<String> = Response()

        val user = userRepository.findByUsername(username)

        if(user.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("No user was found with this username {$username}.")

            return response
        }

        userRepository.delete(user.get())

        response.messages.add("${user.get().username} was deleted successfully.")

        return response

    }

    override fun newUser(newUser: User): Response<User> {

        val response: Response<User> = Response()


        if(validateUserBody(user=newUser, response = response)){
            response.messages.add("Signed up successfully.")
            newUser.password = bCryptPasswordEncoder.encode(newUser.password)
            response.data = userRepository.save(newUser)
        }

        return response

    }

    override fun getAllUsers(usersPerPage: Int, pageIndex: Int): Response<Page<User>> {
        val response = Response<Page<User>>()
        val page = Page<User>()

        page.totItems = userRepository.count().toInt()

        page.totPages = page.totItems!! / usersPerPage

        page.page = pageIndex

        page.data = userRepository.paginated(PageRequest.of(pageIndex,usersPerPage))

        response.status = HttpStatus.OK
        response.success = true
        response.data = page

     return response
    }

    override fun addRole(username: String, roleId: Long): Response<User> {
        val response: Response<User> = Response()

        val user = userRepository.findByUsername(username)


        if(user.isEmpty){
            response.messages.add("User not found.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return response
        }

        val role = roleRepository.findById(roleId)

        if(role.isEmpty){
            response.messages.add("Role not found.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return response
        }

        val roles = HashSet(user.get().roles)

        if(roles.any { it.id == roleId }){
            response.messages.add("{$username} already owns this role.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return response
        }

        roles.add(role.get())
        user.get().roles = roles
        response.data = userRepository.save(user.get())
        response.messages.add("Role was added successfully.")

        return response
    }

    override fun removeRole(username: String, roleId: Long): Response<User> {
        val response: Response<User> = Response()

        val user = userRepository.findByUsername(username)

        if(user.isEmpty){
            response.messages.add("User not found.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return response
        }

        val role = roleRepository.findById(roleId)

        if(role.isEmpty){
            response.messages.add("Role not found.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return response
        }

        val roles = HashSet(user.get().roles)


        if(!roles.removeIf { it.id == roleId }){

            response.messages.add("{$username} does not own this role.")
            response.status = HttpStatus.BAD_REQUEST
            response.success = false

            return response
        }

        user.get().roles = roles
        response.data = userRepository.save(user.get())
        response.messages.add("Role removed successfully.")

        return response
    }

    override fun changePassword(username: String): Response<User> {
        val response: Response<User> = Response()
        //TODO
        return response
    }

    override fun resetPassword(email: String): Response<User> {
        val response: Response<User> = Response()
        //TODO
        return response
    }


}