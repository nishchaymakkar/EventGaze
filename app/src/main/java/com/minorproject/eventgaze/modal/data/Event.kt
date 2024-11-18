package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("event_description")
    val eventDescription: String,
    @SerialName("event_id")
    val eventId: String,
    @SerialName("event_name")
    val eventName: String,
    @SerialName("event_scope")
    val college: List<College>,
    @SerialName("event_tags")
    val eventTags: String,
    @SerialName("publisher_id")
    val publisherId: Int? = null,
    @SerialName("event_category")
    val eventCategory: EventCategory,
    @SerialName("event_art")
    val eventArt: String? = null,
    @SerialName("event_date")
    val eventDate: String ?= null,
)