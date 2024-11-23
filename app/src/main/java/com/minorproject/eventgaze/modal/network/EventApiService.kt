package com.minorproject.eventgaze.modal.network



import com.google.gson.GsonBuilder
import com.minorproject.eventgaze.modal.User
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.data.Login
import com.minorproject.eventgaze.modal.data.PublisherSignUp
import com.minorproject.eventgaze.modal.data.StudentSignUp
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
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import javax.inject.Singleton


private const val BASE_URL = "http://192.168.1.3:8080/eventgaze/"


val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object LoginApi{
    val retrofitService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }
}
object CategoryApi{
    val retrofitService: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }
}
object CollegeApi{
    val retrofitService: CollegeApiService by lazy {
        retrofit.create(CollegeApiService::class.java)
    }
}

object EventApi {
    val retrofitService: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }
}
interface CategoryApiService{
    @GET("category/getAll")
    suspend fun getCategory(): List<EventCategory>
}

interface CollegeApiService{
    @GET("collegeList/getAll")
    suspend fun getCollegeList(): List<College>
}

interface LoginApiService{
    @POST("auth/student")
    suspend fun registerStudent(@Body student: StudentSignUp):Response<Void>


    @POST("auth/publisher")
    suspend fun registerPublisher(@Body publisher: PublisherSignUp):Response<Void>

    @POST("auth/login")
    suspend fun login(@Body user: User): Response<Login>
}
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
        @Part eventArt: MultipartBody.Part
    ): Response<Void>

}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
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

}

