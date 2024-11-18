package com.minorproject.eventgaze.ui.screens.user.homescreen


import com.minorproject.eventgaze.modal.data.Event


sealed interface EventUiState {
    //data class EventFetchById(val event: Event): EventUiState
    data class Success(val event: List<Event>) : EventUiState
    data class Error(val error: String): EventUiState
    object Loading: EventUiState
}