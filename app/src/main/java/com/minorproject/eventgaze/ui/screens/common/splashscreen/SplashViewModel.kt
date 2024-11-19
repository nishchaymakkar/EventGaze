package com.minorproject.eventgaze.ui.screens.common.splashscreen

import androidx.lifecycle.ViewModel
import com.minorproject.eventgaze.HomeScreenP
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.SignInScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import com.minorproject.eventgaze.SplashScreen
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
   // configurationService: ConfigurationService,

) : ViewModel(){
 fun onAppStart(openAndPopUp: (String,String)-> Unit){

//     when (accountService.role) {
//         "user" -> {if (accountService.hasUser) openAndPopUp(MainScreen, SplashScreen) }
//         "publisher" -> {if (accountService.hasUser) openAndPopUp(HomeScreenP,
//             SplashScreen)}
//         else -> openAndPopUp(SignInScreen, SplashScreen)
//     }
     openAndPopUp(MainScreen, SplashScreen)
     openAndPopUp(SignInScreen, SplashScreen)

 }
}