package com.minorproject.eventgaze.ui.screens.publisher.addeventscreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.Event.Log
import com.google.firebase.storage.storage
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.data.categories
import com.minorproject.eventgaze.modal.network.EventApi
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val eventRepository: EventRepository
):ViewModel() {

    private val _publishEventResult = MutableLiveData<Result<String>>(null)
    val publishEventResult: LiveData<Result<String>> = _publishEventResult
   var uiState = mutableStateOf(AddEventUiState())
        private set

    private val eventName
        get() = uiState.value.eventName
    private val eventDescription
        get() = uiState.value.eventDescription
    private val eventScope
        get() = uiState.value.eventScope
    private val eventCategory
        get() = uiState.value.eventCategory



    private val eventTags
        get() = uiState.value.eventTags
    private val _selectedImageUri = mutableStateOf<Uri?>(null)

    val selectedImageUri: State<Uri?> = _selectedImageUri

    // Function to update the selected image URI
    fun onImageSelected(uri: Uri?) {
        _selectedImageUri.value = uri
    }
    fun popUp(popUp: ()-> Unit){
        popUp()
    }
    fun onEventNameChange(newValue: String){
        uiState.value = uiState.value.copy(eventName = newValue)
    }
    fun onEventDescriptionChange(newValue: String){
        uiState.value = uiState.value.copy(eventDescription = newValue)
    }
    fun onEventTagsChange(newValue: String){
        uiState.value = uiState.value.copy(eventTags = newValue)
    }
    fun onEventCategoryChange(newValue: String){
        uiState.value = uiState.value.copy(eventCategory = newValue)
    }
    fun onEventScopeChange(newValue: String){
        uiState.value = uiState.value.copy(eventScope = newValue)
    }

    fun publishEvent(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val imageUri = selectedImageUri.value
            val event = Event(
                eventId = UUID.randomUUID().toString(),
                eventName = eventName,
                eventDescription = eventDescription,
                eventTags = eventTags,
                eventScope = eventScope,
                categoryId = when (eventCategory) {
                    "Technology" -> 4
                    "Sports" -> 1
                    "Arts" -> 6
                    "Debates" -> 3
                    "Music" -> 2
                    "Cooking" -> 5
                    else -> 0
                },
            )

            if (imageUri != null) {
                val result = eventRepository.postEventToServer(event, imageUri, context)
            //    val mappedResult = result.map { "Upload successful" } // Maps Result<Unit> to Result<String>

                _publishEventResult.value = result

                if (result.isSuccess) {
                    onSuccess()
                } else {
                    // Log the error with the exception message if available
                //    Log.e("PublishEvent", "Event upload failed: ${mappedResult.exceptionOrNull()?.message}")
                    onFailure()
                }
            } else {
            //    Log.e("PublishEvent", "Image selection is required")
                _publishEventResult.value = Result.failure(Exception("Image selection is required"))
                onFailure()
            }
        }
    }

}

