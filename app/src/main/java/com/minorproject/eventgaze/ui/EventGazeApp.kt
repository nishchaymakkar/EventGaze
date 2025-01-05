@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)

package com.minorproject.eventgaze.ui

import android.content.res.Resources
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minorproject.eventgaze.EventGazeAppState
import com.minorproject.eventgaze.SignInScreen
import com.minorproject.eventgaze.SignUpScreen
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import com.minorproject.eventgaze.ui.screens.common.loginscreen.SignInScreen
import com.minorproject.eventgaze.ui.screens.common.signupscreen.SignUpScreen
import com.minorproject.eventgaze.ui.screens.common.splashscreen.SplashScreen
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.gson.Gson
import com.minorproject.eventgaze.AddEventScreen
import com.minorproject.eventgaze.CollegeEventScreen
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.DetailScreenForLinks
import com.minorproject.eventgaze.HomeScreenP
import com.minorproject.eventgaze.ui.screens.user.homescreen.MainScreen
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.PiScreen
import com.minorproject.eventgaze.ProfileScreenP
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.ui.screens.publisher.addeventscreen.AddEventScreen
import com.minorproject.eventgaze.ui.screens.publisher.homescreen.HomeScreen
import com.minorproject.eventgaze.ui.screens.publisher.profilescreen.ProfileScreen
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.CollegeEventScreen
import com.minorproject.eventgaze.ui.screens.user.detailScreen.DetailScreen
import com.minorproject.eventgaze.ui.screens.user.detailScreen.DetailScreenForLinks
import com.minorproject.eventgaze.ui.screens.user.homescreen.MainScreenViewModel
import com.minorproject.eventgaze.ui.screens.user.profilescreen.PiScreen
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"
@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember {SnackbarHostState()},
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(
    snackbarHostState,
    navController,
    snackbarManager,
    resources,
    coroutineScope
) {
    EventGazeAppState(
        snackbarHostState,
        navController,
        snackbarManager,
        resources,
        coroutineScope
    )
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}







@ExperimentalSharedTransitionApi
@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun EventGazeApp() {
    EventGazeTheme {

val context = LocalContext.current
            val appState = rememberAppState()
            val eventViewModel : MainScreenViewModel = viewModel()



        LaunchedEffect(Unit) {
            // Handle intent deep link if available
            (context as? ComponentActivity)?.intent?.data?.let { uri ->
                val eventId = uri.lastPathSegment // Extract eventId from /events/id/{eventId}
                if (eventId != null) {
                    appState.navigate("$DetailScreenForLinks/$eventId")

                }
            }
        }
       Surface (color = MaterialTheme.colorScheme.onPrimary) {
            Scaffold(
                modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary),
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.scaffoldHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        }
                    )
                },
            ) { innerPaddingModifier ->
                val innerPadding = innerPaddingModifier
                SharedTransitionLayout {
                    NavHost(
                        navController = appState.navController,
                        startDestination = SplashScreen,
                    ) {
                        composable(SplashScreen) {
                            SplashScreen(openAndPopUp = { route, popUp ->
                                appState.navigateAndPopUp(
                                    route,
                                    popUp
                                )
                            })
                        }
                        composable(SignInScreen) {
                            SignInScreen(openAndPopUp = { route, popUp ->
                                appState.navigateAndPopUp(
                                    route,
                                    popUp
                                )
                            },
                                navigate = { route -> appState.navigate(route) })
                        }
                        composable(SignUpScreen) {
                            SignUpScreen(navigate = { route -> appState.clearAndNavigate(route) },
                                popUp = { appState.popUp() })
                        }

                        composable(MainScreen) {
                            MainScreen(
                                navigate = { route -> appState.navigate(route) },
                                restartApp = { route -> appState.clearAndNavigate(route) },
                                animatedVisibilityScope = this,
                                eventUiState = eventViewModel.eventUiState,
                                retryAction = eventViewModel::getEvents,
                                categoryUiState = eventViewModel.categoryUiState,
                            )

                        }
                        composable(
                            "$DetailScreen/{eventJson}",
                            arguments = listOf(navArgument("eventJson") {
                                type = NavType.StringType
                            })
                        )
                        { backStackEntry ->

                            val eventJson = backStackEntry.arguments?.getString("eventJson")
                            val event = eventJson?.let { json ->
                                java.net.URLDecoder.decode(json, "UTF-8")
                                    .let { decodeJson ->
                                        Gson().fromJson(
                                            decodeJson,
                                            Event::class.java
                                        )
                                    }
                            }
                            if (event != null) {
                                DetailScreen(
                                    animatedVisibilityScope = this,
                                    event = event,
                                    popUp = { appState.popUp() }
                                )
                            }
                        }
                        composable(
                            "$CollegeEventScreen/{college}",
                            arguments = listOf(navArgument("college") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val collegeJson = backStackEntry.arguments?.getString("college")
                            val college = collegeJson?.let { json ->
                                java.net.URLDecoder.decode(json, "UTF-8")
                                    .let { decodeJson ->
                                        Gson().fromJson(
                                            decodeJson,
                                            College::class.java
                                        )
                                    }
                            }

                            if (college != null) {
                                CollegeEventScreen(
                                    college = college,
                                    popUp = { appState.popUp()},
                                    navigate = { route -> appState.navigate(route) },
                                    eventUiState = eventViewModel.eventUiState,
                                    animatedVisibilityScope = this
                                )
                            }
                        }
                        composable(HomeScreenP) {
                            HomeScreen(
                                navigate = { route -> appState.navigate(route) },
                                eventUiState = eventViewModel.eventUiState,
                                retryAction = eventViewModel::getEvents,
                                animatedVisibilityScope = this
                            )
                        }
                        composable(ProfileScreenP) {
                            ProfileScreen(
                                restartApp = { route -> appState.clearAndNavigate(route) },
                            )
                        }
                        composable(PiScreen) {
                            PiScreen(popUp = { appState.popUp() })
                        }
                        composable(AddEventScreen) {
                            AddEventScreen(
                                popUp = { appState.popUp() },
                                retry = eventViewModel::getEvents,
                                modifier = Modifier.sharedBounds(
                                    sharedContentState = rememberSharedContentState(
                                        key = FAB_EXPLODE_BOUNDS_KEY
                                    ),
                            animatedVisibilityScope = this
                            )
                            )
                        }
                        composable("$DetailScreenForLinks/{eventId}",
                            arguments = listOf(navArgument("eventId") {
                                type = NavType.StringType
                            }),
                            deepLinks = listOf(navDeepLink {
                                uriPattern = "http://192.168.1.5:8080/events/eventId/id/{eventId}"
                            })
                        ) { backStackEntry ->
                            val eventId = backStackEntry.arguments?.getString("eventId")
                            DetailScreenForLinks(
                                eventId = eventId,
                                popUp = { appState.popUp() }
                            )
                        }
                    }
                }
            }
        }
        }
    }
