package com.minorproject.e_commerce.ui.screens.splashscreen

import com.minorproject.e_commerce.HomeScreen
import com.minorproject.e_commerce.MainScreen
import com.minorproject.e_commerce.SignInScreen
import com.minorproject.e_commerce.SignUpScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.e_commerce.SplashScreen
import com.minorproject.e_commerce.model.service.AccountService
import com.minorproject.e_commerce.model.service.LogService
import com.minorproject.e_commerce.ui.ECommerceViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
   // configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService,
) : ECommerceViewModel(logService){
 fun onAppStart(openAndPopUp: (String,String)-> Unit){
     if (accountService.hasUser) openAndPopUp(MainScreen, SplashScreen)
     else openAndPopUp(SignInScreen, SplashScreen)
 }
}