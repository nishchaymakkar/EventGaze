package com.minorproject.eventgaze.modal.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.ui.graphics.vector.ImageVector

data class Category(
    val id: Int,
    val name: String,
   // val icon: ImageVector
)
val categories = listOf(
    Category(0, "All"),
    Category(1, "Sports"),
    Category(2, "Music"),
    Category(3, "Debates"),
    Category(4, "Technologies"),
    Category(5,"Cooking"),
    Category(6,"Arts")
)