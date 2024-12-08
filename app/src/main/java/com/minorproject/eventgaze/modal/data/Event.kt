package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("event_description")
    val eventDescription: String,
    @SerialName("event_id")
    val eventId: Long,
    @SerialName("event_name")
    val eventName: String,
    @SerialName("college_id")
    val college: College,
    @SerialName("event_tags")
    val eventTags: String,
    @SerialName("publisher")
    val publishers: Publisher? = null,
    @SerialName("event_category")
    val eventCategory: EventCategory,
    @SerialName("event_art")
    val eventArt: String? = null,
    @SerialName("event_date")
    val eventDate: String ?= null,
    @SerialName("event_venue")
    val eventVenue: String
)