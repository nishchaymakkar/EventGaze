package com.minorproject.eventgaze.ui.screens.common.splashscreen

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.HomeScreenP
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.SignInScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.modal.datastore.PreferencesRepository
import com.minorproject.eventgaze.modal.network.EventRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    @ApplicationContext private val context: Context
) : ViewModel(){

    init {
        viewModelScope.launch {
            preferencesRepository.preloadSessionToken()
        }
    }
    val sessionToken: Flow<String?> = preferencesRepository.sessionToken
    val userRole: Flow<String?> = preferencesRepository.userRole

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            val token = sessionToken.firstOrNull() // Collect the session token
            val role = userRole.firstOrNull() // Collect the user role

            if (token != null) {
                when (role) {
                    "STUDENT" -> openAndPopUp(MainScreen, SignInScreen)
                    "PUBLISHER" -> openAndPopUp(HomeScreenP, SignInScreen)
                    else -> openAndPopUp(SignInScreen, SignInScreen) // Default case
                }
            } else {
                openAndPopUp(SignInScreen, SignInScreen) // Handle unauthenticated state
            }
        }
    }
}