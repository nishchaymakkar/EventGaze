@file:OptIn(ExperimentalMaterial3Api::class)

package com.minorproject.eventgaze.ui

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.minorproject.eventgaze.CollegeEventScreen
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.HomeScreenP
import com.minorproject.eventgaze.ui.screens.user.homescreen.MainScreen
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.PiScreen
import com.minorproject.eventgaze.ProfileScreenP
import com.minorproject.eventgaze.model.data.Event
import com.minorproject.eventgaze.ui.screens.publisher.homescreen.HomeScreen
import com.minorproject.eventgaze.ui.screens.publisher.profilescreen.ProfileScreen
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.CollegeEventScreen
import com.minorproject.eventgaze.ui.screens.user.detailScreen.DetailScreen
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import com.minorproject.eventgaze.ui.screens.user.homescreen.MainScreenViewModel
import com.minorproject.eventgaze.ui.screens.user.profilescreen.PiScreen
import com.minorproject.eventgaze.ui.theme.EventGazeTheme

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

        Surface(color = MaterialTheme.colorScheme.primary) {
            val appState = rememberAppState()
            val eventViewModel : MainScreenViewModel = viewModel()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.scaffoldHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = MaterialTheme.colorScheme.secondary,
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    )
                },
            ) { innerPaddingModifier ->
                val innerPadding = innerPaddingModifier
                SharedTransitionScope {
                    NavHost(
                        navController = appState.navController,
                        startDestination = SplashScreen,
                    ) {
                        composable(SplashScreen){
                            SplashScreen(openAndPopUp = {route, popUp -> appState.navigateAndPopUp(route, popUp)})
                        }
                        composable(SignInScreen){
                            SignInScreen(openAndPopUp = {route,popUp -> appState.navigateAndPopUp(route, popUp)},
                                navigate = {route -> appState.navigate(route)})
                        }
                        composable(SignUpScreen){
                            SignUpScreen(navigate = {route -> appState.clearAndNavigate(route)},
                                popUp = {  appState.popUp()})
                        }

                        composable(MainScreen){
                            MainScreen(navigate = {route -> appState.navigate(route)},
                                animatedVisibilityScope = this@composable,
                                eventUiState = eventViewModel.eventUiState,
                                retryAction = eventViewModel::getEvents
                            )

                        }
                        composable("$DetailScreen/{eventId}",
                            arguments = listOf(navArgument("eventId"){type = NavType.IntType})
                        ){backStackEntry ->
                            val eventId = backStackEntry.arguments?.getInt("eventId")
                        //    DetailScreen(eventId = eventId, eventUiState = eventViewModel.eventUiState, animatedVisibilityScope = this@composable)
                        }
                        composable("$CollegeEventScreen/{collegeId}",
                            arguments = listOf(navArgument("collegeId"){type = NavType.IntType})
                        ) { backStackEntry ->
                            val collegeId = backStackEntry.arguments?.getInt("collegeId")
                            CollegeEventScreen(collegeId = collegeId, detailnavigate = {route -> appState.navigate(route)}, animatedVisibilityScope = this)
                        }
                        composable(HomeScreenP) {
                            HomeScreen(navigate = {route -> appState.navigate(route)})
                        }
                        composable(ProfileScreenP) {
                            ProfileScreen()
                        }
                        composable(PiScreen) {
                            PiScreen()
                        }
                    }
                }
            }
        }
    }
}