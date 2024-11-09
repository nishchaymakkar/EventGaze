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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.data.items
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.CollegesScreen
import com.minorproject.eventgaze.ui.screens.user.profilescreen.ProfileScreen
import com.minorproject.eventgaze.ui.theme.onPrimary
import com.minorproject.eventgaze.ui.theme.onPrimary1
import com.minorproject.eventgaze.ui.theme.primary1
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable


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
    val selectedItemIndex = viewModel.selectedItemIndex
    val hazeState = remember { HazeState() }
    val onShareClick: (Event) -> Unit = {event ->
        val shareLink = viewModel.getShareableLink(event)

      shareEvent(context,shareLink)
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary).fillMaxSize(),
        bottomBar = {
            AnimatedNavigationBar( // Set the bottom bar background to transparent
               modifier = Modifier.height(64.dp).hazeChild(hazeState).padding(horizontal = 36.dp, vertical = 8.dp),
                selectedIndex = selectedItemIndex,
                cornerRadius= shapeCornerRadius(50.dp ),
                ballAnimation = Teleport(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = MaterialTheme.colorScheme.onPrimary.copy(.8f),
                ballColor = MaterialTheme.colorScheme.primary
            ) {
                items.forEachIndexed { index, item ->
                   Box(
                       modifier = Modifier.fillMaxSize()
                           .noRippleClickable {
                               viewModel.onBottomNavItemClick(index)
                           },
                       contentAlignment = Alignment.Center
                   ){
                            Icon(
                                modifier = Modifier.size(26.dp),

                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = null,
                                tint = if (index == selectedItemIndex) MaterialTheme.colorScheme.primary else
                                MaterialTheme.colorScheme.secondary.copy(.4f)
                            )
                        }
                }
            }

        }
    ) { paddingValues ->
        val padding = paddingValues
        Column(
            Modifier
                .fillMaxSize(),
        ) {
            when (selectedItemIndex) {
                0 -> {
                    when (eventUiState){
                        is EventUiState.Loading -> ShimmerListItem(isLoading = true)
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
                                        category = categoryUiState.category
                                    )
                                }
                            }



                        }
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
        type = "text/html"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Link via"))
}

