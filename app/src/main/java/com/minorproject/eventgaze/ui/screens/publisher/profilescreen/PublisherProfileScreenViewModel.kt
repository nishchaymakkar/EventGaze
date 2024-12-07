package com.minorproject.eventgaze.ui.screens.publisher.profilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.SignInScreen
import com.minorproject.eventgaze.modal.datastore.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublisherProfileScreenViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
)
    : ViewModel()  {

        fun signOut(restartApp: (String) -> Unit) {
            viewModelScope.launch {
                preferencesRepository.clearPreferences()
            }
            restartApp(SignInScreen)
        }
}