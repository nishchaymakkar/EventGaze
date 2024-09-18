

package com.minorproject.e_commerce.ui.screens.signupscreen

import androidx.compose.runtime.mutableStateOf
import com.minorproject.e_commerce.R.string as AppText
import com.minorproject.e_commerce.SignInScreen
import com.minorproject.e_commerce.model.service.AccountService
import com.minorproject.e_commerce.model.service.LogService
import com.minorproject.e_commerce.model.service.isValidEmail
import com.minorproject.e_commerce.model.service.isValidPassword
import com.minorproject.e_commerce.model.service.passwordMatches
import com.minorproject.e_commerce.ui.ECommerceViewModel
import com.minorproject.e_commerce.ui.common.components.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.minorproject.e_commerce.SignUpScreen

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : ECommerceViewModel(logService) {
  var uiState = mutableStateOf(SignUpUiState())
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

    launchCatching {
      accountService.signUp(email,password)
      navigate(SignInScreen)
    }
  }
  fun onSignInClick(popUp: () -> Unit){
    popUp()
  }
}
