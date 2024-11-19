package com.minorproject.eventgaze.modal.data

data class StudentSignUp(
    val firstName: String,
    val lastName: String,
    val userEmail: String,
    val userPassword: String,
    val collegeId: Long
)