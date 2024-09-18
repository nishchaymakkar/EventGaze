package com.minorproject.e_commerce.model.service

import com.minorproject.e_commerce.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val current: Flow<User>


    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)

    //    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
    suspend fun signUp(email: String, password: String): Result<Unit>
    fun getUserName(): String?
}