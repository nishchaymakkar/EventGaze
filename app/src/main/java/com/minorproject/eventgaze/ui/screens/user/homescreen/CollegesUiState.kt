package com.minorproject.eventgaze.ui.screens.user.homescreen

import com.minorproject.eventgaze.modal.data.College

sealed interface CollegeUiState {
    data class Success(val category: List<College>) :CollegeUiState
    data class Error(val error: String): CollegeUiState
    object Loading: CollegeUiState
}