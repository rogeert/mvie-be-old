package com.rogeert.mviebe.util

class Page<T> {

    var page: Int ? = 0

    var totPages: Int  ? = 0

    var data:List<T> = arrayListOf()

    var totItems: Int ? = 0

    var pageSize: Int ? = 0

}