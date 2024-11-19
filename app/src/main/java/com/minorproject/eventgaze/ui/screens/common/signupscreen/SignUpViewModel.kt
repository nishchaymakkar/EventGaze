

package com.minorproject.eventgaze.ui.screens.common.signupscreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.R.string as AppText
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.PublisherSignUp
import com.minorproject.eventgaze.modal.data.StudentSignUp
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.modal.data.isValidEmail
import com.minorproject.eventgaze.modal.data.isValidPassword
import com.minorproject.eventgaze.modal.data.passwordMatches
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import com.minorproject.eventgaze.ui.screens.user.homescreen.CollegeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val eventRepository: EventRepository
): ViewModel() {
  private val _collegeOptions = MutableStateFlow<List<College>>(emptyList())
  val collegeOptions : StateFlow<List<College>> = _collegeOptions
  var uiState = mutableStateOf(SignUpUiState())
  var collegeUiState: CollegeUiState by mutableStateOf(CollegeUiState.Loading)
    private set
  private val publisherOrgName
    get() = uiState.value.publisherOrgName
  private val email
    get() = uiState.value.userEmail
  private val password
    get() = uiState.value.userPassword
  private val firstName
    get() = uiState.value.firstName
  private val lastName
    get() = uiState.value.lastName
  private val college
    get() = uiState.value.college

  var selectedTabIndex by  mutableStateOf(0)
    private set

  fun onSelectedTabChange(index: Int){
    selectedTabIndex = index
  }
  fun onCollegeChange(newValue: College){
    uiState.value = uiState.value.copy(college = newValue)
  }

  fun onFirstNameChange(newValue: String) {
    uiState.value = uiState.value.copy(firstName = newValue)
  }

  fun onPublisherOrgNameChange(newValue: String) {
    uiState.value = uiState.value.copy(publisherOrgName =  newValue)
  }

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(userEmail = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(userPassword =  newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(navigate: (String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }
    if (selectedTabIndex == 0){
      registerStudent(studentSignUp = StudentSignUp(
        firstName = firstName,
        lastName = lastName,
        userEmail =  email,
        userPassword = password,
        collegeId = college.collegeId
      ),
        onSuccess = { navigate(com.minorproject.eventgaze.SignInScreen)},
        onFailure = {})
    } else {
      registerPublisher(
        publisherSignUp = PublisherSignUp(
          publisherOrgName = publisherOrgName,
          userEmail = email,
          userPassword =  password
        ),
        onSuccess = {navigate(com.minorproject.eventgaze.SignInScreen)},
        onFailure = {}
      )
    }



  }
  init {
      getCollegeList()
  }
  fun getCollegeList() {
    viewModelScope.launch {

      withContext(Dispatchers.IO){
        collegeUiState = CollegeUiState.Loading

        val result = eventRepository.fetchCollegeList()
        if (result.isSuccess) {
          _collegeOptions.value = result.getOrNull().orEmpty()
        } else {
          // Handle the error case, e.g., log the error or show a message
          result.exceptionOrNull()?.printStackTrace()
        }
      }
    }
  }
  fun registerStudent(studentSignUp: StudentSignUp,onSuccess:()-> Unit, onFailure:()-> Unit){
    viewModelScope.launch {
      val result = eventRepository.signUpForUser(studentSignUp)
      if (result.isSuccess) {
          onSuccess()
      } else {
        onFailure()
      }
    }
  }

  fun registerPublisher(publisherSignUp: PublisherSignUp,onSuccess:()-> Unit, onFailure:()-> Unit){
    viewModelScope.launch {
      val result = eventRepository.signUpForPublisher(publisherSignUp)
      if (result.isSuccess) {
        onSuccess()
      } else {
        onFailure()
      }
    }
  }
  fun onSignInClick(popUp: () -> Unit){
    popUp()
  }
}
