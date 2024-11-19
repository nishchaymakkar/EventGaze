package com.minorproject.eventgaze.modal.data

data class Login(
    val sessionToken: String,
    val userRole: String,
    val userId: Long
)
