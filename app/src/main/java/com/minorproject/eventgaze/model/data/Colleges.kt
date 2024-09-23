package com.minorproject.eventgaze.model.data

import androidx.annotation.DrawableRes
import com.minorproject.eventgaze.R

data class College(
    val collegeName: String,
    val collegeId: Int,
   @DrawableRes val collegeImg: Int,
)
val  colleges = listOf(
    College("IIT Selampur",1, R.drawable.iitselampur),
    College("Maharaja Surajmal Institute of Technology",2, R.drawable.surajmal),
    College("Maharaja Agarsen Institute of Technology",3, R.drawable.agarsen),
    College("Bhagwan Parshuram Institute of Technology",4, R.drawable.bpit),
    College("Bharti Vidyapeeth College of Engineering",5, R.drawable.bvce),
    College("Guru Tegh Bahadur Institute Of Engineering",6, R.drawable.gtbit),
)