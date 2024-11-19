package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Publisher(
    @SerialName("publishers")
    val publishers: Publishers
)