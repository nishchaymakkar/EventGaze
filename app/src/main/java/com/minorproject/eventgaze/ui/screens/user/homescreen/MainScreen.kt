package com.minorproject.eventgaze.ui.screens.user.homescreen

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.items
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.CollegesScreen
import com.minorproject.eventgaze.ui.screens.user.profilescreen.ProfileScreen
import dev.chrisbanes.haze.HazeState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.MainScreen(
    restartApp: (String) -> Unit,
    retryAction: () -> Unit,
    eventUiState: EventUiState,
    categoryUiState: CategoryUiState,
    viewModel: MainScreenViewModel = hiltViewModel(),
    navigate: (String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val context = LocalContext.current
    val collegeOptions by viewModel.collegeOptions.collectAsState()
    val selectedItemIndex = viewModel.selectedItemIndex
   val hazeState = remember { HazeState() }
    val onShareClick: (Event) -> Unit = { event ->
        val shareLink = viewModel.getShareableLink(event)

      shareEvent(context,shareLink)
    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary).fillMaxSize(),
        topBar = {
            TopAppBar(title = { when(selectedItemIndex){
                0 -> Text(text = "Discover", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)
                1 -> Text(text = "Colleges", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)
                2 -> Text(text = "", style = MaterialTheme.typography.headlineSmall)
            } }, colors = TopAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.secondary,
                actionIconContentColor = Color.Unspecified
            )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimary) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor =MaterialTheme.colorScheme.secondary.copy(.6f),
                            disabledTextColor = MaterialTheme.colorScheme.secondary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            disabledIconColor = MaterialTheme.colorScheme.secondary,
                            selectedIndicatorColor = Color.Transparent
                        ),
                        selected = selectedItemIndex == index,
                        onClick = { viewModel.onBottomNavItemClick(index)},
                        label = {
                           //Text(text = item.title, fontWeight = FontWeight.ExtraBold)
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
                }}

        }
    ) { it ->

        Column(
            Modifier
                .padding(it).fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (selectedItemIndex) {
                0 -> {
                    when (eventUiState){
                        is EventUiState.Loading -> CircularProgressIndicator()
                        is EventUiState.Error -> {
                            println("Error: ${eventUiState.error}")
                            ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
                        }
                        is EventUiState.Success -> {
                            when( categoryUiState){
                                is CategoryUiState.Loading -> ShimmerListItem(isLoading = true)
                                is CategoryUiState.Error -> {
                                    println("Error: ${categoryUiState.error}")
                                    ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
                                }
                                is CategoryUiState.Success -> {
                                    HomeScreenContent(

                                        event = eventUiState.event,
                                        onItemClick = { event->
                                            viewModel.onItemClick(event, navigate)
                                        },
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        onShareClick = onShareClick,
                                        refresh = retryAction,
                                        category = categoryUiState.category,
                                        modifier = Modifier.padding(),
                                        isloading = false
                                    )
                                }
                            }



                        }
                    }

                }
                1 -> CollegesScreen(

                    onCollegeClick = { college, _ ->
                        viewModel.onCollegeClick(college, navigate)
                    },
                    colleges = collegeOptions,
                    animatedVisibilityScope = animatedVisibilityScope
                )
                2 -> {
                    val username by viewModel.userName.collectAsState()
                    ProfileScreen(
                        onSignOutClick = {
                            viewModel.onSignOutClick(restartApp) },
                        username = username,
                        onPiClick = {viewModel.onPiClick(navigate)}
                    )
                }
            }
        }
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

fun shareEvent(context: Context, shareLink: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,shareLink)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, null))
}

