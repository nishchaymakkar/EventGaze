package com.minorproject.eventgaze.modal.data

data class EventRequestDto(

    val eventDescription: String,
    val eventId: String,
    val eventName: String,
    val college: College,
    val eventTags: String,
    val userId: Long,
    val eventCategory: EventCategory,
    //   val eventArt: String,
    val eventDate: String,
    val eventVenue: String
)
