package com.minorproject.eventgaze.ui.screens.user.homescreen


import com.minorproject.eventgaze.modal.data.EventCategory

sealed interface CategoryUiState {
    data class Success(val category: List<EventCategory>) : CategoryUiState
    data class Error(val error: String): CategoryUiState
    object Loading: CategoryUiState
}