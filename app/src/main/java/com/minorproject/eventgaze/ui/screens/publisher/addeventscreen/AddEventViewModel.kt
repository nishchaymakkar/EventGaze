package com.minorproject.eventgaze.ui.screens.publisher.addeventscreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.network.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val eventRepository: EventRepository
):ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _publishEventResult = MutableLiveData<Result<Unit>>(null)
    val publishEventResult: LiveData<Result<Unit>> = _publishEventResult
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

    fun popUp(popUp: () -> Unit) {
        popUp()
    }

    fun onEventNameChange(newValue: String) {
        uiState.value = uiState.value.copy(eventName = newValue)
    }

    fun onEventDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(eventDescription = newValue)
    }

    fun onEventTagsChange(newValue: String) {
        uiState.value = uiState.value.copy(eventTags = newValue)
    }

    fun onEventCategoryChange(newValue: EventCategory) {
        uiState.value = uiState.value.copy(eventCategory = newValue)
    }

    fun onEventScopeChange(newValue: String) {
        uiState.value = uiState.value.copy(eventScope = newValue)
    }

    fun publishEvent(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val imageUri = selectedImageUri.value
            val event = Event(
                eventId = UUID.randomUUID().toString(),
                eventName = eventName,
                eventDescription = eventDescription,
                eventTags = eventTags,
                eventScope = eventScope,
                eventCategory = eventCategory
            )


            if (imageUri != null) {
                val result = eventRepository.postEventToServer(event, imageUri, context)


                if (result.isSuccess) {
                    _publishEventResult.value = Result.success(Unit)
                    onSuccess()
                } else {
                    _publishEventResult.value = Result.failure(Exception("Upload failed"))
                    onFailure()
                }
            } else {

                _publishEventResult.value = Result.failure(Exception("Image selection is required"))
                onFailure()
            }

            _isLoading.value = false
        }
    }

}

