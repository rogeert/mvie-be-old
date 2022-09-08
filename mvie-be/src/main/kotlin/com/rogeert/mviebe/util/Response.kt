package com.rogeert.mviebe.util

import lombok.Data
import org.springframework.http.HttpStatus

@Data
class Response<T> {

    var messages: ArrayList<String> = ArrayList()

    var success: Boolean ? = true

    var data: T ? = null

    var status: HttpStatus ? = HttpStatus.OK
}