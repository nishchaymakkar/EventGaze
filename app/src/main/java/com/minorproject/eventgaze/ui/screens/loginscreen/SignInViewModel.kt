package com.minorproject.eventgaze.ui.screens.loginscreen

import androidx.compose.runtime.mutableStateOf
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.SignInScreen
import com.minorproject.eventgaze.R.string as AppText
import com.minorproject.eventgaze.SignUpScreen
import com.minorproject.eventgaze.model.service.AccountService
import com.minorproject.eventgaze.model.service.LogService
import com.minorproject.eventgaze.model.service.isValidEmail
import com.minorproject.eventgaze.ui.EventGazeViewModel
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : EventGazeViewModel(logService) {
    var uiState = mutableStateOf(SignInState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

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

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(MainScreen, SignInScreen)
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }
    fun onSignUpClick(navigate: (String) -> Unit )= navigate(SignUpScreen)

}
