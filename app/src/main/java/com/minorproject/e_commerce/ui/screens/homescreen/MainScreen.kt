package com.minorproject.e_commerce.ui.screens.homescreen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.e_commerce.model.data.items
import com.minorproject.e_commerce.ui.screens.mycartscreen.MyCartScreenContent
import com.minorproject.e_commerce.ui.screens.profilescreen.ProfileScreenContent


@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navigate: (String) -> Unit
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
                0 -> HomeScreenContent(query = "", onQueryChange = {}, modifier = Modifier)
                1 -> MyCartScreenContent(Modifier)
                2 -> {val username by  viewModel.userName.collectAsState()
                    ProfileScreenContent(
                        Modifier,
                        onSignOutClick = { viewModel.onSignOutClick(navigate) },

                        username = username
                    )
                }
            }
        }
    }
}