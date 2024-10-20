package com.minorproject.eventgaze.model.network

import com.minorproject.eventgaze.model.data.Event
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private const val BASE_URL = "https://10.0.2.2:80/"


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()



object EventApi {
    val retrofitService: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }
}


interface EventApiService {

    @GET("events")
    suspend fun getEvents(): List<Event>

//    @GET("events/{id}")
//    suspend fun getEventById(@Path("id") eventId: String): Event

}