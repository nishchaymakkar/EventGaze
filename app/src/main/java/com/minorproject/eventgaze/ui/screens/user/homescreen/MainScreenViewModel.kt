package com.minorproject.eventgaze.ui.screens.user.homescreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.ui.EventGazeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(

    private val eventRepository: EventRepository
): ViewModel(){

    var selectedItemIndex  by mutableStateOf(0)
        private set
    var eventUiState: EventUiState by mutableStateOf(EventUiState.Loading)
        private set
    var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Loading)

    init {
        getEvents()
        getCategory()

    }
    fun getShareableLink(event: Event): String {
        val baseUrl = "http://192.168.1.5:8080"
        val eventLink = "$baseUrl/events/id/${event.eventId}"
        return eventLink
    }

    fun onBottomNavItemClick(index: Int) {
        selectedItemIndex = index
    }

    fun onSignOutClick(restartApp: (String) -> Unit) {


    }
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

//init {
//    loadUserName()
//}
    fun onPiClick(navigateAndPopUp: (String) -> Unit) = navigateAndPopUp(PiScreen)

    fun onItemClick(event: Event, navigate: (String) -> Unit){
        val eventJson = Gson().toJson(event)
        val encodedEventJson = java.net.URLEncoder.encode(eventJson,"UTF-8")
        val destination ="$DetailScreen/$encodedEventJson"
        navigate(destination)
    }
    fun onCollegeClick(college:College, navigate: (String) -> Unit){
        val collegeJson = Gson().toJson(college)
        val encodedCollegeJson = java.net.URLEncoder.encode(collegeJson,"UTF-8")
        val destination = "${CollegeEventScreen}/$encodedCollegeJson"
        navigate(destination)
    }
    // Function to load the user's name from AccountService
//    private fun loadUserName() {
//        viewModelScope.launch {
////            val name = accountService.getUserName()
////            Log.d("UserViewModel", "Username: $name")
//            _userName.value
//        }
//    }
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

    fun getCategory() {
        viewModelScope.launch {

            withContext(Dispatchers.IO){
                categoryUiState = CategoryUiState.Loading
                val result = eventRepository.fetchCategory()

                categoryUiState = when {
                    result.isSuccess -> {
                        Log.d("MainScreenVieModel","Category fetched successfully: ${result.getOrThrow().size}")
                        CategoryUiState.Success(result.getOrThrow())
                    }
                    result.isFailure -> {
                        val exception = result.exceptionOrNull()
                        Log.e("MainScreenViewModel", "Error: ${exception?.localizedMessage}")
                        when (exception) {
                            is IOException -> CategoryUiState.Error("Network error occurred. Please check your connection.")
                            is HttpException -> CategoryUiState.Error("Server error occurred. Please try again later.")
                            else -> CategoryUiState.Error("An unknown error occurred. Please try again.")
                        }

                    }
                    else -> CategoryUiState.Error("Unexpected Error")
                }
            }

        }


    }


}