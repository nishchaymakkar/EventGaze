package com.minorproject.eventgaze.ui.screens.user.colleges_screen

import androidx.lifecycle.ViewModel
import com.minorproject.eventgaze.DetailScreen

class CollegeEventViewModel(): ViewModel() {
    fun onItemClick(eventid: Int, navigate: (String)-> Unit){
        val destination = "$DetailScreen/$eventid"
        navigate(destination)
    }
}