package com.minorproject.eventgaze.modal.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    var authToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
//get the token from event Repository and send it dynamically to app

        authToken?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
            Log.d("AuthInterceptor", "Token added: $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}


