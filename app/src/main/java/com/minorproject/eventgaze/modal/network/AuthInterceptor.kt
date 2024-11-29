package com.minorproject.eventgaze.modal.network

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import com.minorproject.eventgaze.modal.datastore.PreferencesRepository
import com.minorproject.eventgaze.modal.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.internal.InjectedFieldSignature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthInterceptor @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        // Fetch the token synchronously
        val token =  preferencesRepository.getCachedSessionToken()
        // Build the new request with the Authorization header
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
            Log.d("AuthInterceptor", "Token added: $it")
        }

        // Proceed with the request
        return chain.proceed(requestBuilder.build())
    }
}
