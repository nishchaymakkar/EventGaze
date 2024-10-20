package com.minorproject.eventgaze.ui.screens.user.homescreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.items
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.CollegesScreen
import com.minorproject.eventgaze.ui.screens.user.profilescreen.ProfileScreen

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.MainScreen(
    retryAction: () -> Unit,
    eventUiState: EventUiState,
    viewModel: MainScreenViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimary) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedIconColor =MaterialTheme.colorScheme.secondary,
                            disabledTextColor = MaterialTheme.colorScheme.secondary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            disabledIconColor = MaterialTheme.colorScheme.secondary,
                            selectedIndicatorColor = MaterialTheme.colorScheme.primary
                        ),
                        selected = selectedItemIndex == index,
                        onClick = { selectedItemIndex = index },
                        label = {
                            Text(text = item.title, fontWeight = FontWeight.ExtraBold)
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        val padding = paddingValues
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color = MaterialTheme.colorScheme.onPrimary)

        ) {
            when (selectedItemIndex) {
                0 -> {
                    when (eventUiState){
                        is EventUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
                        is EventUiState.Error -> {
                            println("Error: ${eventUiState.error}")
                            ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
                        }
                        is EventUiState.Success ->  HomeScreenContent(
                            event = eventUiState.event,
                            onItemClick = { event, _ ->
                                viewModel.onItemClick(event.id, navigate)
                            },
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }

                }
                1 -> CollegesScreen(

                    onCollegeClick = { college, _ ->
                        viewModel.onCollegeClick(college.collegeId, navigate)
                    },
                    animatedVisibilityScope = animatedVisibilityScope
                )
                2 -> {
                    val username by viewModel.userName.collectAsState()
                    ProfileScreen(
                        onSignOutClick = {
                            viewModel.onSignOutClick(navigate) },
                        username = username,
                        onPiClick = {viewModel.onPiClick(navigate)}
                    )
                }
            }
        }
    }
}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = modifier, color = MaterialTheme.colorScheme.primary)
    }

}
@Composable
fun ErrorScreen(modifier: Modifier = Modifier,retryAction: ()-> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Connection failed", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.primary)
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))}
    }
}