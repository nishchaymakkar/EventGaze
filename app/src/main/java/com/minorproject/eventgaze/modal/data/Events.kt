package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Events(
    @SerialName("eventArt")
    val eventArt: String,
    @SerialName("eventCategory")
    val eventCategory: EventCategory,
    @SerialName("eventDate")
    val eventDate: String,
    @SerialName("eventDescription")
    val eventDescription: String,
    @SerialName("eventId")
    val eventId: Int,
    @SerialName("eventName")
    val eventName: String,
    @SerialName("eventScope")
    val eventScope: String,
    @SerialName("eventTags")
    val eventTags: String,
    @SerialName("publishers")
    val publishers: String
)