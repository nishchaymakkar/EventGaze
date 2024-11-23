package com.minorproject.eventgaze.modal.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Publishers(
    @SerialName("publisher_id")
    val publisherId: Long,
    @SerialName("publisher_img")
    val publisherImage: String,
    @SerialName("publisher_org_name")
    val publisherOrgName: String? = null
)