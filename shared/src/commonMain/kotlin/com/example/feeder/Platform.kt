package com.example.feeder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform