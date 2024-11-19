package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventCategory(
    @SerialName("event_category_id")
    val categoryId: Long,
    @SerialName("category_name")
    val categoryName: String
)