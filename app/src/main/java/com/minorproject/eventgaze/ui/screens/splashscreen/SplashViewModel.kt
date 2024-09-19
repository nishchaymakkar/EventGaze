package com.minorproject.eventgaze.ui.screens.splashscreen

import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.SignInScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.model.service.AccountService
import com.minorproject.eventgaze.model.service.LogService
import com.minorproject.eventgaze.ui.EventGazeViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
   // configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService,
) : EventGazeViewModel(logService){
 fun onAppStart(openAndPopUp: (String,String)-> Unit){
     if (accountService.hasUser) openAndPopUp(MainScreen, SplashScreen)
     else openAndPopUp(SignInScreen, SplashScreen)
 }
}