package com.minorproject.e_commerce.ui.screens.homescreen

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.e_commerce.HomeScreen
import com.minorproject.e_commerce.MyCartScreen
import com.minorproject.e_commerce.ProfileScreen
import com.minorproject.e_commerce.SplashScreen
import com.minorproject.e_commerce.model.service.AccountService
import com.minorproject.e_commerce.model.service.LogService
import com.minorproject.e_commerce.ui.ECommerceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
): ECommerceViewModel(logService){
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