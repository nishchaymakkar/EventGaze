package com.minorproject.eventgaze.ui.screens.common.loginscreen

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.minorproject.eventgaze.HomeScreenP
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.SignInScreen
import com.minorproject.eventgaze.R.string as AppText
import com.minorproject.eventgaze.SignUpScreen
import com.minorproject.eventgaze.model.service.AccountService
import com.minorproject.eventgaze.model.service.LogService
import com.minorproject.eventgaze.model.service.isValidEmail
import com.minorproject.eventgaze.model.service.isValidPassword
import com.minorproject.eventgaze.ui.EventGazeViewModel
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val GOOGLE_SIGN_IN_REQUEST_CODE = 200


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : EventGazeViewModel(logService) {
    var uiState = mutableStateOf(SignInState())
        private set
    private val role
        get() = uiState.value.role
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
//        when (role) {
//            "user" -> {
//                if (!email.isValidEmail()) {
//                    SnackbarManager.showMessage(AppText.email_error)
//                    return
//                }
//
//                if (password.isBlank()) {
//                    SnackbarManager.showMessage(AppText.empty_password_error)
//                    return
//                }
//
//                launchCatching {
//                    accountService.authenticate(email, password)
//                    openAndPopUp(MainScreen, SignInScreen)
//                }
//            }
//            "publisher" -> {
//                if (!email.isValidEmail()) {
//                    SnackbarManager.showMessage(AppText.email_error)
//                    return
//                }
//
//                if (password.isBlank()) {
//                    SnackbarManager.showMessage(AppText.empty_password_error)
//                    return
//                }
//
//                launchCatching {
//                    accountService.authenticate(email, password)
//                    openAndPopUp(HomeScreenP, SignInScreen)
//                }
//            }
//        }



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



    fun getGoogleSignInIntent(activity: Activity): Intent {
        return accountService.signInWithGoogle(activity)
    }


    fun handleGoogleSignInResult(account: GoogleSignInAccount, openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            val result = accountService.handleGoogleSignInResult(account)
            if (result.isSuccess) {
                openAndPopUp(MainScreen, SignInScreen)
            } else {
                SnackbarManager.showMessage(AppText.sign_in_failed)
            }
        }
    }

}
