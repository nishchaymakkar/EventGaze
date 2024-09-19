package com.minorproject.eventgaze.ui.screens.homescreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.model.service.AccountService
import com.minorproject.eventgaze.model.service.LogService
import com.minorproject.eventgaze.ui.EventGazeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
): EventGazeViewModel(logService){
    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SplashScreen)
        }
    }
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    init {
        // Load the user's name when the ViewModel is created
        loadUserName()
    }

    // Function to load the user's name from AccountService
    private fun loadUserName() {
        viewModelScope.launch {
            val name = accountService.getUserName()
            Log.d("UserViewModel", "Username: $name")
            _userName.value = name
        }
    }
}