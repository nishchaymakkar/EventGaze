package com.minorproject.eventgaze.ui.screens.user.colleges_screen

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.modal.data.Event

class CollegeEventViewModel(): ViewModel() {
    fun onItemClick(event: Event, navigate: (String) -> Unit){
        val eventJson = Gson().toJson(event)
        val encodedEventJson = java.net.URLEncoder.encode(eventJson,"UTF-8")
        val destination ="$DetailScreen/$encodedEventJson"
        navigate(destination)
    }
}