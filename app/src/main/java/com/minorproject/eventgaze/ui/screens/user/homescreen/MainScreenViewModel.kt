package com.minorproject.eventgaze.ui.screens.user.homescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.Gson
import com.minorproject.eventgaze.CollegeEventScreen
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.PiScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.modal.service.AccountService
import com.minorproject.eventgaze.modal.service.LogService
import com.minorproject.eventgaze.ui.EventGazeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val eventRepository: EventRepository
): EventGazeViewModel(logService){

    var selectedItemIndex  by mutableStateOf(0)
        private set
    var eventUiState: EventUiState by mutableStateOf(EventUiState.Loading)
        private set

    init {
        getEvents()

    }
    fun getShareableLink(event: Event): String {
        val baseUrl = "http://192.168.1.6:8080"
        val eventLink = "$baseUrl/events/id/${event.eventId}"
        return eventLink
    }

    fun onBottomNavItemClick(index: Int) {
        selectedItemIndex = index
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

    fun onItemClick(eventId: String?, navigate: (String) -> Unit){
//        val eventJson = Gson().toJson(event)
//        val encodedEventJson = java.net.URLEncoder.encode(eventJson,"UTF-8")
        val destination ="$DetailScreen/$eventId"
        navigate(destination)
    }
    fun onCollegeClick(collegeId: Int, navigate: (String) -> Unit){
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

                withContext(Dispatchers.IO){
                    eventUiState = EventUiState.Loading
                    val result = eventRepository.fetchEvents()

                    eventUiState = when {
                        result.isSuccess -> {
                            Log.d("MainScreenVieModel","Events fetched successfully: ${result.getOrThrow().size}")
                            EventUiState.Success(result.getOrThrow())
                        }
                        result.isFailure -> {
                            val exception = result.exceptionOrNull()
                            Log.e("MainScreenViewModel", "Error: ${exception?.localizedMessage}")
                            when (exception) {
                                is IOException -> EventUiState.Error("Network error occurred. Please check your connection.")
                                is HttpException -> EventUiState.Error("Server error occurred. Please try again later.")
                                else -> EventUiState.Error("An unknown error occurred. Please try again.")
                            }

                        }
                        else -> EventUiState.Error("Unexpected Error")
                    }
                }

            }


    }


}