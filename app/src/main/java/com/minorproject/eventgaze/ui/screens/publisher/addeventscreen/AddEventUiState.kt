package com.minorproject.eventgaze.ui.screens.publisher.addeventscreen

import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.EventCategory

data class AddEventUiState(
    val eventName: String = "",
    val eventDescription:String = "",
    val college: College = College("", 0L, ""),
    val eventTags: String = "",
    val eventCategory: EventCategory = EventCategory(0L, ""),
)