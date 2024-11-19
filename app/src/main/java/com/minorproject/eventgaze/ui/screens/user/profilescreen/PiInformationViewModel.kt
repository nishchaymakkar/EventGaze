package com.minorproject.eventgaze.ui.screens.user.profilescreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PiInformationViewModel @Inject constructor(
   // private val accountService: AccountService
): ViewModel() {
    var uiState = mutableStateOf(PiUiState())
        private set

    private val _userName = MutableStateFlow<String?>(null)



    private val userName
        get() = _userName

    private val emailId
        get() = uiState.value.emailId
    private val collegeName
        get() = uiState.value.collegeName


    fun onUserNameChange(newValue: String) {
        uiState.value = uiState.value.copy(userName = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(emailId = newValue)
    }

    fun onCollegeNameChange(newValue: String) {
        uiState.value = uiState.value.copy(collegeName = newValue)
    }
}