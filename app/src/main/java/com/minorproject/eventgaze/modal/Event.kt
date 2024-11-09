package com.minorproject.eventgaze.modal


import com.minorproject.eventgaze.modal.data.EventCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Event(
    @SerialName("event_description")
    val eventDescription: String,
    @SerialName("event_id")
    val eventId: String,
    @SerialName("event_name")
    val eventName: String,
    @SerialName("event_scope")
    val eventScope: String,
    @SerialName("event_tags")
    val eventTags: String,
    @SerialName("publisher_id")
    val publisherId: Int? = null,
    @SerialName("event_category")
    val eventCategory: EventCategory,
    @SerialName("event_art")
    val eventArt: String? = null
)