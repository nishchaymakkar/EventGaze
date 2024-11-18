package com.minorproject.eventgaze.ui.screens.user.detailScreen


import com.minorproject.eventgaze.modal.data.Event


sealed interface EventDetailUiState {
    data class Success(val event: Event) : EventDetailUiState
    data class Error(val error: String): EventDetailUiState
    object Loading: EventDetailUiState
}