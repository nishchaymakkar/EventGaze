package com.minorproject.eventgaze.ui.screens.publisher.addeventscreen

data class AddEventUiState(
    val eventName: String = "",
    val eventDescription:String = "",
    val eventScope: String = "",
    val eventTags: String = "",
    val eventCategory: String = "",
)