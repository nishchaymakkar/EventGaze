package com.minorproject.eventgaze.modal.network

import android.content.Context
import android.net.Uri
import android.util.Log
import coil.network.HttpException
import com.google.gson.Gson
import com.minorproject.eventgaze.modal.User
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.data.EventRequestDto
import com.minorproject.eventgaze.modal.data.Login
import com.minorproject.eventgaze.modal.data.PublisherSignUp
import com.minorproject.eventgaze.modal.data.StudentSignUp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

class EventRepository @Inject constructor(
  private val eventApiService: EventApiService
) {
    @Volatile
    private var token: String? = null
    suspend fun fetchCategory(): Result<List<EventCategory>> {
        return try {
            val categories = eventApiService.getCategory()
            Result.success(categories)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchEvents(): Result<List<Event>> {
        return try {
            val events = eventApiService.getEvents()
            Result.success(events)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun signUpForUser(studentSignUp: StudentSignUp): Result<String> {
        return try {
            val response = eventApiService.registerStudent(studentSignUp)
            if (response.isSuccessful) {
                Result.success("User signed up successfully")
            } else {
                Result.failure(Exception("Failed to sign up user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
    suspend fun signUpForPublisher(publisherSignUp: PublisherSignUp): Result<String> {
        return try {
            val response = eventApiService.registerPublisher(publisherSignUp)
            if (response.isSuccessful) {
                Result.success("User signed up successfully")
            } else {
                Result.failure(Exception("Failed to sign up user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun login(user: User): Result<Login>{
        return try {
            val response = eventApiService.login(user)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    Result.success(loginResponse) // Return the Login object on success
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception("Failed to sign in user: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun fetEventById(eventId: String?): Result<Event>{
       return try {
           val event = eventApiService.getEventById(eventId)
           Result.success(event)
       } catch (e: Exception ){
           Result.failure(e)

       } catch (e: Exception) {
           Result.failure(e)
       }
    }
    suspend fun postEventToServer(event: EventRequestDto, imageUri: Uri, context: Context): Result<String> {
        try {
            val contentResolver = context.contentResolver
            val fileInputStream = contentResolver.openInputStream(imageUri) ?: return Result.failure(Exception("File not found"))

            val requestFile = RequestBody.create(
                contentResolver.getType(imageUri)?.toMediaTypeOrNull(),
                fileInputStream.readBytes()
            )
            fileInputStream.close() // Closing after reading

            val uniqueFileName = UUID.randomUUID().toString() + ".jpg"


            val filePart = MultipartBody.Part.createFormData("eventArt", uniqueFileName, requestFile)






            val eventName = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventName)
            val eventDescription = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventDescription)
            val eventCategory = RequestBody.create("text/plain".toMediaTypeOrNull(),event.eventCategory.categoryId.toString())
            val eventDate = RequestBody.create("text/plain".toMediaTypeOrNull(), LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy")))
            val eventScope = RequestBody.create("text/plain".toMediaTypeOrNull(), event.college.collegeName)
            val eventTags = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventTags)
            val publishers = RequestBody.create("text/plain".toMediaTypeOrNull(), event.userId.toString())
            val collegeId = RequestBody.create("text/plain".toMediaTypeOrNull(), event.college.collegeId.toString())

            val response = eventApiService.createEvent(
                eventName, eventCategory,eventDescription, eventDate, eventScope, eventTags,collegeId, publishers,  filePart
            )

//
           return if (response.isSuccessful) {

                Log.d("API Success", "event created successfully")
                Result.success("isSuccessful")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("API Response Error", "Error response: $errorBody")
                Result.failure(Exception("Failed to create event: $errorBody"))
            }


        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }

    suspend fun fetchCollegeList(): Result<List<College>> {
        return try {
            val colleges = eventApiService.getCollegeList()
            Result.success(colleges)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }




}
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEventRepository(eventApiService: EventApiService): EventRepository {
        return EventRepository(eventApiService)
    }
}
