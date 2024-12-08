package com.minorproject.eventgaze.ui.screens.publisher.homescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.network.HttpException
import com.minorproject.eventgaze.AddEventScreen
import com.minorproject.eventgaze.ProfileScreenP
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeScreenPublisherViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    var eventUiState: EventUiState by mutableStateOf(EventUiState.Loading)
        private set

    private val _deleteEventState = MutableStateFlow<Result<Unit>?>(null)
    val deleteEventState: StateFlow<Result<Unit>?> = _deleteEventState
    fun getEvents() {

        viewModelScope.launch {
            eventUiState = EventUiState.Loading
            val result = eventRepository.fetchEvents()

            eventUiState = when {
                result.isSuccess -> {
                    //Log.d("MainScreenVieModel","Events fetched successfully: ${result.getOrThrow().size}")
                    EventUiState.Success(result.getOrThrow())
                }
                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    Log.e("MainScreenViewModel", "Error: ${exception?.localizedMessage}")
                    when (exception) {
                        is IOException -> EventUiState.Error("Network error occurred. Please check your connection.")
                        is HttpException -> EventUiState.Error("Server error occurred. Please try again later.")
                        else -> EventUiState.Error("An unknown error occurred. Please try again.")
                    }

                }
                else -> EventUiState.Error("Unexpected Error")
            }
        }
    }
    fun onProfileClick(navigate: (String)-> Unit){
        navigate(ProfileScreenP)
    }
    fun onAddEventClick(navigate: (String)-> Unit){
        navigate(AddEventScreen)
    }
    fun onDeleteClick(eventId: Long){
        viewModelScope.launch {
         _deleteEventState.value =   eventRepository.deleteEvent(eventId)
        }
    }
}