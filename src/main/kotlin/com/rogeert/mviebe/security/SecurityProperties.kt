package com.rogeert.mviebe.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class SecurityProperties {

    @Value("\${secret}")
    var secret = ""

    var expirationTime: Int = 31 // in days

    var strength = 10

    // constant
    val tokenPrefix = "Bearer"
    val headerString = "Authorization"

}