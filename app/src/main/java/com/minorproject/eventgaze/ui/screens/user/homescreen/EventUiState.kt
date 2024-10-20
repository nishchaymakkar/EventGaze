package com.minorproject.eventgaze.ui.screens.user.homescreen

import com.minorproject.eventgaze.model.data.Event

sealed interface EventUiState {
    data class Success(val event: List<Event>) : EventUiState
    data class Error(val error: String): EventUiState
    object Loading: EventUiState
}