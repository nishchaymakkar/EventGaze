package com.minorproject.eventgaze.model.service

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.minorproject.eventgaze.model.User
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

    fun signInWithGoogle(activity: Activity): Intent
    suspend fun handleGoogleSignInResult(account: GoogleSignInAccount): Result<Unit>


}