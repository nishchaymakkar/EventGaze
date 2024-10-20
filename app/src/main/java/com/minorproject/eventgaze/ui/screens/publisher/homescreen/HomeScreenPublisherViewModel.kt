package com.minorproject.eventgaze.ui.screens.publisher.homescreen

import androidx.lifecycle.ViewModel
import com.minorproject.eventgaze.ProfileScreenP

class HomeScreenPublisherViewModel: ViewModel() {
    fun onProfileClick(navigate: (String)-> Unit){
        navigate(ProfileScreenP)
    }
}