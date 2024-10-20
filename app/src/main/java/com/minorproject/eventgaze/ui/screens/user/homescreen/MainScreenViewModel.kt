package com.minorproject.eventgaze.ui.screens.user.homescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.minorproject.eventgaze.CollegeEventScreen
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.PiScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.model.network.EventApi
import com.minorproject.eventgaze.model.service.AccountService
import com.minorproject.eventgaze.model.service.LogService
import com.minorproject.eventgaze.ui.EventGazeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
): EventGazeViewModel(logService){
    var eventUiState: EventUiState by mutableStateOf(EventUiState.Loading)
        private set

    init {
        getEvents()
    }
    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            Firebase.auth.signOut()
            restartApp(SplashScreen)
        }

    }
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    init {
        // Load the user's name when the ViewModel is created
        loadUserName()
    }
    fun onPiClick(navigateAndPopUp: (String) -> Unit) = navigateAndPopUp(PiScreen)

    fun onItemClick(eventId: Int,navigate: (String) -> Unit){
        val destination ="$DetailScreen/$eventId"
        navigate(destination)
    }
    fun onCollegeClick(collegeId: Int,navigate: (String) -> Unit){
        val destination = "${CollegeEventScreen}/$collegeId"
        navigate(destination)
    }
    // Function to load the user's name from AccountService
    private fun loadUserName() {
        viewModelScope.launch {
            val name = accountService.getUserName()
            Log.d("UserViewModel", "Username: $name")
            _userName.value = name
        }
    }
     fun getEvents() {
        viewModelScope.launch {
            eventUiState = EventUiState.Loading
            try {
                val listResult = EventApi.retrofitService.getEvents()
                Log.d("MainScreenViewModel", "Events fetched successfully: ${listResult.size}")
                eventUiState = EventUiState.Success(
                    listResult.toList()
                )
            } catch (e: IOException) {
                Log.e("MainScreenViewModel", "Network Error: ${e.localizedMessage}")
                eventUiState = EventUiState.Error("Network error occurred. Please check your connection.")
            } catch (e: HttpException) {
                Log.e("MainScreenViewModel", "HTTP Error: ${e.localizedMessage}")
                eventUiState = EventUiState.Error("Server error occurred. Please try again later.")
            } catch (e: Exception) {
                Log.e("MainScreenViewModel", "Unknown Error: ${e.localizedMessage}")
                eventUiState = EventUiState.Error("An unknown error occurred. Please try again.")
            }
        }
    }

}