
package com.minorproject.eventgaze.ui.screens.common.signupscreen

import com.minorproject.eventgaze.modal.data.College

data class SignUpUiState(
 val publisherOrgName: String = "",
  val publisherEmail: String = "",
  val userEmail: String = "",
  val userPassword: String = "",
  val firstName: String ="",
  val lastName: String = "",
  val repeatPassword: String = "",
 val college: College = College("",0L,"")
)
