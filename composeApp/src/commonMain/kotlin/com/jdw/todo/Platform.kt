package com.jdw.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform