/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.minorproject.eventgaze.modal.service.impl

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.util.trace
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.User
import com.minorproject.eventgaze.modal.service.AccountService
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AccountService {

  override val currentUserId: String
    get() = auth.currentUser?.uid.orEmpty()

  override val hasUser: Boolean
    get() = auth.currentUser != null

 // override val role: String = "publisher"

 override val current: Flow<User>
    get() = callbackFlow {
      val listener =
        FirebaseAuth.AuthStateListener { auth ->
          this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
        }
      auth.addAuthStateListener(listener)
      awaitClose { auth.removeAuthStateListener(listener) }
    }

  override suspend fun authenticate(email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password).await()
  }



  override suspend fun sendRecoveryEmail(email: String) {
    auth.sendPasswordResetEmail(email).await()
  }



  override suspend fun linkAccount(email: String, password: String): Unit =
    trace(LINK_ACCOUNT_TRACE) {
      val credential = EmailAuthProvider.getCredential(email, password)
      auth.currentUser!!.linkWithCredential(credential).await()
    }

  override suspend fun deleteAccount() {
    auth.currentUser!!.delete().await()
  }

  override suspend fun signOut() {
    if (auth.currentUser!!.isAnonymous) {
      auth.currentUser!!.delete()
    }
    auth.signOut()

    // Sign the user back in anonymously.

  }
    override suspend fun signUp(email: String, password: String): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserName(): String? {
        return auth.currentUser?.displayName
    }


    companion object {
    private const val LINK_ACCOUNT_TRACE = "linkAccount"
  }
    override fun signInWithGoogle(activity: Activity): Intent {
        val googleSignInClient = GoogleSignIn.getClient(activity, getGoogleSignInOptions(activity))

        googleSignInClient.signOut().addOnCompleteListener{

        }
        return googleSignInClient.signInIntent
    }

    private fun getGoogleSignInOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Your Web Client ID from Firebase
            .requestEmail()
            .build()
    }

    override suspend fun handleGoogleSignInResult(account: GoogleSignInAccount): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}