package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventCategory(
    @SerialName("event_category_id")
    val eventCategoryId: Long,
    @SerialName("category_name")
    val eventName: String
)