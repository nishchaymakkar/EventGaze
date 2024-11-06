package com.minorproject.eventgaze.modal.network

import android.content.Context
import android.net.Uri
import android.util.Log
import coil.network.HttpException
import com.minorproject.eventgaze.modal.Event
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class EventRepository @Inject constructor() {

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

            val filePart = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)


            val eventName = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventName)
            val eventDescription = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventDescription)
            val eventDate = RequestBody.create("text/plain".toMediaTypeOrNull(), "28-10-2023")
            val eventScope = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventScope)
            val eventTags = RequestBody.create("text/plain".toMediaTypeOrNull(), event.eventTags)

            val response = EventApi.retrofitService.createEvent(
              eventName, eventDescription, eventDate, eventScope, eventTags, filePart
            )

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                Log.e("API Response Error", "Error response: $errorBody")
            }
            return if (response.isSuccessful) {
                Result.success(response.body() ?: "Success")

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