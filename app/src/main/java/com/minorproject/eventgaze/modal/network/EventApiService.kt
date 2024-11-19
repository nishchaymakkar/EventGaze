package com.minorproject.eventgaze.modal.network



import com.google.gson.GsonBuilder
import com.minorproject.eventgaze.modal.User
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.data.Login
import com.minorproject.eventgaze.modal.data.PublisherSignUp
import com.minorproject.eventgaze.modal.data.StudentSignUp
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


private const val BASE_URL = "http://192.168.1.7:8080/eventgaze/"
private val gson = GsonBuilder()
    .setLenient()
    .create()
private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
private val client = OkHttpClient.Builder().addInterceptor(logging).build()



private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
   .client(client)
    .build()
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

    @GET("events/id/{myId}")
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