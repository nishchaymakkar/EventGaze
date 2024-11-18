package com.minorproject.eventgaze.modal.data

import androidx.annotation.DrawableRes
import com.minorproject.eventgaze.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class College(
    @SerialName("college_name")
    val collegeName: String,
    @SerialName("college_id")
    val collegeId: Long,
    @SerialName("collegeAddress")
    val collegeLocation: String,
   //@DrawableRes val collegeImg: Int,
)
//val  colleges = listOf(
//    College("IIT Selampur",1, "New Delhi, Delhi",R.drawable.iitselampur),
//    College("Maharaja Surajmal Institute of Technology",2,"New Delhi, Delhi", R.drawable.surajmal),
//    College("Maharaja Agarsen Institute of Technology",3, "New Delhi, Delhi",R.drawable.agarsen),
//    College("Bhagwan Parshuram Institute of Technology",4,"New Delhi, Delhi", R.drawable.bpit),
//    College("Bharti Vidyapeeth College of Engineering",5,"New Delhi, Delhi", R.drawable.bvce),
//    College("Guru Tegh Bahadur Institute Of Engineering",6,"New Delhi, Delhi", R.drawable.gtbit),
//    College("IIT Selampur",1, "New Delhi, Delhi",R.drawable.iitselampur),
//    College("Maharaja Surajmal Institute of Technology",2,"New Delhi, Delhi", R.drawable.surajmal),
//    College("Maharaja Agarsen Institute of Technology",3, "New Delhi, Delhi",R.drawable.agarsen),
//    College("Bhagwan Parshuram Institute of Technology",4,"New Delhi, Delhi", R.drawable.bpit),
//    College("Bharti Vidyapeeth College of Engineering",5,"New Delhi, Delhi", R.drawable.bvce),
//    College("Guru Tegh Bahadur Institute Of Engineering",6,"New Delhi, Delhi", R.drawable.gtbit),
//)