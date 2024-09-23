package com.minorproject.eventgaze.model.data

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
    val icon: ImageVector
)
val categories = listOf(
    Category(1, "All", Icons.Default.Category),
    Category(2, "Sports", Icons.Default.SportsKabaddi),
    Category(3, "Music", Icons.Default.MusicNote),
    Category(4, "Debates", Icons.Default.People),
    Category(5, "Technologies", Icons.Default.Computer),
    Category(6,"Cooking", Icons.Default.Kitchen)
)