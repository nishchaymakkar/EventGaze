package com.minorproject.eventgaze.ui.screens.user.detailScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.minorproject.eventgaze.modal.network.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel@Inject constructor(private val eventRepository: EventRepository): ViewModel() {
    var eventDetailUiState: EventDetailUiState by mutableStateOf(EventDetailUiState.Loading)
        private set
    fun getEventById(eventId: String?){
        viewModelScope.launch {
            eventDetailUiState = EventDetailUiState.Loading
            val result = eventRepository.fetEventById(eventId = eventId)
            eventDetailUiState = when {
                result.isSuccess -> {
                    Log.d("MainScreenVieModel","Events fetched successfully: ${result.getOrThrow()}")
                    EventDetailUiState.Success(result.getOrThrow())
                }
                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    Log.e("MainScreenViewModel", "Error: ${exception?.localizedMessage}")
                    when (exception) {
                        is IOException -> EventDetailUiState.Error("Network error occurred. Please check your connection.")
                        is HttpException -> EventDetailUiState.Error("Server error occurred. Please try again later.")
                        else -> EventDetailUiState.Error("An unknown error occurred. Please try again.")
                    }

                }
                else -> EventDetailUiState.Error("Unexpected Error")
            }
        }

    }


}