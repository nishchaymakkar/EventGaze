package com.minorproject.eventgaze.ui.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.model.data.items
import com.minorproject.eventgaze.ui.screens.colleges_screen.CollegesScreen
import com.minorproject.eventgaze.ui.screens.profilescreen.ProfileScreen


@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    detailnavigate: (String) -> Unit
    ){
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimary) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = Color.Gray,
                            disabledTextColor = Color.LightGray,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = Color.Gray,
                            disabledIconColor = Color.LightGray,
                            selectedIndicatorColor = MaterialTheme.colorScheme.primary),
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                        },
                        label = {
                            Text(text = item.title, fontWeight = FontWeight.ExtraBold )
                        },
                        icon = {
//                            BadgedBox(
//                                badge = {
//                                    if(item.badgeCount != null){
//                                        Badge {
//                                            Text(text = item.badgeCount.toString())
//                                        }
//                                    }else if (item.hasNews) {
//                                        Badge()
//                                    }
//                                }
//                            ) {
                            Icon(
                                imageVector = if( index == selectedItemIndex) {
                                    item.selectedIcon
                                }else item.unselectedIcon,
                                contentDescription = null
                            )
//                            }
                        }
                    )
                }
            }
        }
    ) {val padding = it
        Column(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onPrimary))
        {
            when (selectedItemIndex) {
                0 -> HomeScreenContent( modifier = Modifier, onItemClick ={ event,_ ->
                   viewModel.onItemClick(event.id, detailnavigate)
                })
                1 -> CollegesScreen(Modifier, onCollegeClick = {college,_ ->
                    viewModel.onCollegeClick(college.collegeId,detailnavigate)
                })
                2 -> {val username by  viewModel.userName.collectAsState()
                    ProfileScreen(
                        Modifier,
                        onSignOutClick = { viewModel.onSignOutClick(navigate) },
                        username = username
                    )
                }
            }
        }
    }
}