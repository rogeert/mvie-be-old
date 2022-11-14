package com.rogeert.mviebe

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class MvieBeApplication

fun main(args: Array<String>) {
    runApplication<MvieBeApplication>(*args)
}