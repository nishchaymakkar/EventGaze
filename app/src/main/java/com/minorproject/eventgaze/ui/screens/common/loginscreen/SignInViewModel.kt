package com.minorproject.eventgaze.ui.screens.common.loginscreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minorproject.eventgaze.HomeScreenP
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.SignInScreen
import com.minorproject.eventgaze.R.string as AppText
import com.minorproject.eventgaze.SignUpScreen
import com.minorproject.eventgaze.modal.User
import com.minorproject.eventgaze.modal.data.Login
import com.minorproject.eventgaze.modal.network.EventRepository
import com.minorproject.eventgaze.modal.data.isValidEmail
import com.minorproject.eventgaze.modal.datastore.PreferencesRepository
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.prefs.Preferences
import javax.inject.Inject

const val GOOGLE_SIGN_IN_REQUEST_CODE = 200


@HiltViewModel
class SignInViewModel @Inject constructor(
   @ApplicationContext private val context: Context,
   private val preferencesRepository: PreferencesRepository,
   private val eventRepository: EventRepository
) : ViewModel() {
    var uiState = mutableStateOf(SignInState())
        private set


    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password




    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }
        viewModelScope.launch {
            val user = User(userName = email, password = password)

            _loginState.value = LoginUiState.Loading // Show loading indicator
            val result = eventRepository.login(user)
            if (result.isSuccess) {
                val loginResponse = result.getOrNull()
                if (loginResponse != null) {
                    _loginState.value = LoginUiState.Success(loginResponse)
                    saveLoginData(loginResponse)

                    if (loginResponse.userRole == "STUDENT") {
                        openAndPopUp(MainScreen, SignInScreen)
                    } else {
                        openAndPopUp(HomeScreenP, SignInScreen)
                    }
                } else {
                    _loginState.value =
                        LoginUiState.Error("Unexpected error: Login response is null")
                }
            } else if (result.isFailure) {
                val exception = result.exceptionOrNull()
                _loginState.value = LoginUiState.Error(exception?.message ?: "Login failed")
            }
        }

    }
    fun saveLoginData(login: Login) {
        viewModelScope.launch {
            preferencesRepository.saveSessionToken(login.sessionToken,login.userRole,login.userId)
        }
    }

    //    fun onForgotPasswordClick() {
//        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)
//            return
//        }
//
//        launchCatching {
//            accountService.sendRecoveryEmail(email)
//            SnackbarManager.showMessage(AppText.recovery_email_sent)
//        }
//    }
    fun onSignUpClick(navigate: (String) -> Unit) {
        navigate(SignUpScreen)
    }


    //    fun getGoogleSignInIntent(activity: Activity): Intent {
//        return accountService.signInWithGoogle(activity)
//    }
//
//
//    fun handleGoogleSignInResult(account: GoogleSignInAccount, openAndPopUp: (String, String) -> Unit) {
//        launchCatching {
//            val result = accountService.handleGoogleSignInResult(account)
//            if (result.isSuccess) {
//                openAndPopUp(MainScreen, SignInScreen)
//            } else {
//                SnackbarManager.showMessage(AppText.sign_in_failed)
//            }
//        }
//    }

}


    sealed class LoginUiState {
        object Idle : LoginUiState()
        object Loading : LoginUiState()
        data class Success(val login: Login) : LoginUiState()
        data class Error(val message: String) : LoginUiState()
    }
