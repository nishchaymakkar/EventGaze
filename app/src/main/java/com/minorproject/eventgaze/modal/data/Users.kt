package com.minorproject.eventgaze.modal.data

import com.google.type.DateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Users (
    @SerialName("user_id")
    val userId: Long,
    @SerialName("user_mail")
    val userEmail: Long,
    @SerialName("user_password")
    val userPassword: String,
    @SerialName("user_role")
    val userRole: String,
    @SerialName("created_at")
   val createdAt: String
)