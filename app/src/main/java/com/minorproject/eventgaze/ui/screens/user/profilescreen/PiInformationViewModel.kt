package com.minorproject.eventgaze.ui.screens.user.profilescreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.modal.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PiInformationViewModel @Inject constructor(
    private val accountService: AccountService
): ViewModel() {
    var uiState = mutableStateOf(PiUiState())
        private set

    private val _userName = MutableStateFlow<String?>(null)

    private fun loadUserName() {
        viewModelScope.launch {
            val name = accountService.getUserName()
            Log.d("UserViewModel", "Username: $name")
            _userName.value = name
        }
    }


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