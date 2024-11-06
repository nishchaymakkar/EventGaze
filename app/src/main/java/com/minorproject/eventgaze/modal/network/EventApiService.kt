package com.minorproject.eventgaze.modal.network



import com.google.gson.GsonBuilder
import com.minorproject.eventgaze.modal.Event
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


private const val BASE_URL = "http://192.168.1.39:8080"
private val gson = GsonBuilder()
    .setLenient()
    .create()
val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
val client = OkHttpClient.Builder().addInterceptor(logging).build()



private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build()



object EventApi {
    val retrofitService: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }
}


interface EventApiService {

    @GET("events")
    suspend fun getEvents(): List<Event>

    @GET("events/id/{myId}")
    suspend fun getEventById(@Path("myId") eventId: String?): Event

    @Multipart
    @POST("events/create")
    suspend fun createEvent(
        @Part("eventName") eventName: RequestBody,
        @Part("eventDescription") eventDescription: RequestBody,
        @Part("eventDate") eventDate: RequestBody,
        @Part("eventScope") eventScope: RequestBody,
        @Part("eventTags") eventTags: RequestBody,
        @Part eventArt: MultipartBody.Part
    ): Response<String>

}