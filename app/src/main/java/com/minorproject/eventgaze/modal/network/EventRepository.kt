package com.minorproject.eventgaze.modal.network

import android.content.Context
import android.net.Uri
import android.util.Log
import coil.network.HttpException
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

class EventRepository @Inject constructor() {
    suspend fun fetchCategory(): Result<List<EventCategory>> {
        return try {
            val categories = CategoryApi.retrofitService.getCategory()
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
            val events = EventApi.retrofitService.getEvents()
            Result.success(events)
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun fetEventById(eventId: String?): Result<Event>{
       return try {
           val event = EventApi.retrofitService.getEventById(eventId)
           Result.success(event)
       } catch (e: Exception ){
           Result.failure(e)

       } catch (e: Exception) {
           Result.failure(e)
       }
    }
    suspend fun postEventToServer(event: Event, imageUri: Uri, context: Context): Result<String> {
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
            val eventCategory = RequestBody.create("text/plain".toMediaTypeOrNull(),event.eventCategory.eventCategoryId.toString())
            val eventDate = RequestBody.create("text/plain".toMediaTypeOrNull(), LocalDate.now().toString().format("yyyy-MM-dd"))
            val eventScope = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventScope)
            val eventTags = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventTags)

            val response = EventApi.retrofitService.createEvent(
              eventName, eventCategory,eventDescription, eventDate, eventScope, eventTags, filePart
            )

//
           return if (response.isSuccessful) {
                val resultBody = response.body() ?: "No response message returned"
                Log.d("API Success", "Server response: $resultBody")
                Result.success(resultBody)
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


}
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEventRepository(): EventRepository {
        return EventRepository()
    }
}