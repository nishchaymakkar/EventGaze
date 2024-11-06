package com.minorproject.eventgaze.modal.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School

val items = listOf(
    BottomNavigationItem(
      //  title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),
    BottomNavigationItem(
       // title = "Colleges",
        selectedIcon = Icons.Filled.School,
        unselectedIcon = Icons.Outlined.School,
        hasNews = false,
        badgeCount = null
    ),
    BottomNavigationItem(
      //  title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        hasNews = false,
    ),
)