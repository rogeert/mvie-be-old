package com.rogeert.mviebe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MvieBeApplication

fun main(args: Array<String>) {
    runApplication<MvieBeApplication>(*args)
}
