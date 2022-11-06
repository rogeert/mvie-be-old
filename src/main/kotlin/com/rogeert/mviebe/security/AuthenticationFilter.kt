package com.rogeert.mviebe.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rogeert.mviebe.dtos.AuthDto
import com.rogeert.mviebe.models.entities.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.Base64
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val authManager: AppAuthenticationManager,
    private val securityProperties: SecurityProperties,
    private val tokenProvider: TokenProvider
) : UsernamePasswordAuthenticationFilter() {


    override fun attemptAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse?
    ): Authentication? {



        return try {
            val mapper = jacksonObjectMapper()

            val base64auth = mapper
                .readValue<AuthDto>(req.inputStream)

            val decoder : Base64.Decoder = Base64.getDecoder()

            val decodedAuth = String(decoder.decode(base64auth.auth))
            val encodedPassword = decodedAuth.substringAfter(":")

            val username = decodedAuth.substringBefore(":")
            val password = String(decoder.decode(encodedPassword))

            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    ArrayList()
                )
            )
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message)
        }
    }


    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain?,
        authentication: Authentication
    ) {
        val token = tokenProvider.createToken(authentication)
        res.addHeader(securityProperties.headerString, token)//"${securityProperties.tokenPrefix} $token"
    }
}
