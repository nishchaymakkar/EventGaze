package com.minorproject.eventgaze.ui.screens.publisher.addeventscreen

import android.content.Context
import android.icu.text.DateFormat
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.data.EventRequestDto
import com.minorproject.eventgaze.modal.datastore.PreferencesRepository
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.ui.screens.user.homescreen.CategoryUiState
import com.minorproject.eventgaze.ui.screens.user.homescreen.CollegeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val eventRepository: EventRepository
):ViewModel() {


    private val _categoryOptions = MutableStateFlow<List<EventCategory>>(emptyList())
    val categoryOptions: StateFlow<List<EventCategory>> = _categoryOptions
    private val _collegeOptions = MutableStateFlow<List<College>>(emptyList())
    val collegeOptions : StateFlow<List<College>> = _collegeOptions
   var collegeUiState: CollegeUiState by mutableStateOf(CollegeUiState.Loading)
        private set

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _publishEventResult = MutableLiveData<Result<Unit>>(null)
    val publishEventResult: LiveData<Result<Unit>> = _publishEventResult
    var uiState = mutableStateOf(AddEventUiState())
        private set

private    var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Loading)
    val sessionToken: Flow<String?> = preferencesRepository.sessionToken
    val userId: Flow<Long?> = preferencesRepository.userId

    private val eventName
        get() = uiState.value.eventName
    private val eventDescription
        get() = uiState.value.eventDescription
    private val college
        get() = uiState.value.college
    private val eventCategory
        get() = uiState.value.eventCategory
    init {
        getCategory()
    }

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

    fun onEventCollegeChange(newValue: College) {
        uiState.value = uiState.value.copy(college = newValue)
    }

    fun publishEvent(context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            val publisherId = preferencesRepository.userId.firstOrNull()
            _isLoading.value = true
            val date = "28-12-2024"
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy") // Matches backend format
            val formattedDate = date.format(formatter) // Ensure 'date' is LocalDate


            val imageUri = selectedImageUri.value
            val event = EventRequestDto(
                eventDescription = eventDescription,
                eventId = UUID.randomUUID().toString(),
                eventName = eventName,
                college = college,
                eventTags = eventTags,
                eventVenue = "",
                eventCategory = eventCategory,
                eventDate = formattedDate,
                userId = publisherId ?: 0L
            )


            if (imageUri != null) {

                        val result =
                            eventRepository.postEventToServer(event, imageUri, context)
                val token = preferencesRepository.sessionToken.firstOrNull()
                if (token != null) {
                  //send the session token to the eventRepository
                   // eventRepository.updateToken(token)
                }

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


    fun getCategory() {
        viewModelScope.launch {

            withContext(Dispatchers.IO){
                categoryUiState = CategoryUiState.Loading
                val result = eventRepository.fetchCategory()
                if (result.isSuccess) {
                    _categoryOptions.value = result.getOrNull().orEmpty().filter { it.categoryId != 0L }
                } else {
                    // Handle the error case, e.g., log the error or show a message
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        }
    }
    init {
        getCollegeList()

    }
  private  fun getCollegeList() {
        viewModelScope.launch {

            withContext(Dispatchers.IO){
                collegeUiState = CollegeUiState.Loading

                val result = eventRepository.fetchCollegeList()
                if (result.isSuccess) {
                    _collegeOptions.value = result.getOrNull().orEmpty()
                } else {
                    // Handle the error case, e.g., log the error or show a message
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        }
    }


}



