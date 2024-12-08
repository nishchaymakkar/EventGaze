package com.minorproject.eventgaze.modal.network



import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.google.gson.GsonBuilder
import com.minorproject.eventgaze.modal.User
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.data.Login
import com.minorproject.eventgaze.modal.data.PublisherSignUp
import com.minorproject.eventgaze.modal.data.StudentSignUp
import com.minorproject.eventgaze.modal.datastore.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val BASE_URL = "http://192.168.1.8:8080/eventgaze/"



interface EventApiService {

    @GET("events/getAll")
    suspend fun getEvents(): List<Event>

    @GET("events/eventId/id/{myId}")
    suspend fun getEventById(@Path("myId") eventId: String?): Event

    @Multipart
    @POST("events/create")
    suspend fun createEvent(
        @Part("eventName") eventName: RequestBody,
        @Part("eventCategoryId") eventCategoryId: RequestBody,
        @Part("eventDescription") eventDescription: RequestBody,
        @Part("eventDate") eventDate: RequestBody,
        @Part("eventScope") eventScope: RequestBody,
        @Part("eventTags") eventTags: RequestBody,
        @Part("colleges") colleges: RequestBody,
        @Part("userId") userId: RequestBody,
        @Part eventArt: MultipartBody.Part
    ): Response<Unit>

    @DELETE("events/id/{myId}")
    suspend fun deleteEvent(@Path("myId") eventId: Long): Response<Unit>

    @POST("auth/student")
    suspend fun registerStudent(@Body student: StudentSignUp):Response<Void>


    @POST("auth/publisher")
    suspend fun registerPublisher(@Body publisher: PublisherSignUp):Response<Void>

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<Login>

    @GET("collegeList/getAll")
    suspend fun getCollegeList(): List<College>

    @GET("category/getAll")
    suspend fun getCategory(): List<EventCategory>
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context,preferencesRepository: PreferencesRepository): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context, preferencesRepository))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideEventApiService(retrofit: Retrofit): EventApiService {
        return retrofit.create(EventApiService::class.java)
    }

}

