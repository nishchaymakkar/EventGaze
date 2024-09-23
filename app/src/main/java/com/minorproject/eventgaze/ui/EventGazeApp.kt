@file:OptIn(ExperimentalMaterial3Api::class)

package com.minorproject.eventgaze.ui

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.collection.intIntMapOf
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minorproject.eventgaze.EventGazeAppState
import com.minorproject.eventgaze.SignInScreen
import com.minorproject.eventgaze.SignUpScreen
import com.minorproject.eventgaze.SplashScreen
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import com.minorproject.eventgaze.ui.screens.loginscreen.SignInScreen
import com.minorproject.eventgaze.ui.screens.signupscreen.SignUpScreen
import com.minorproject.eventgaze.ui.screens.splashscreen.SplashScreen
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.minorproject.eventgaze.CollegeEventScreen
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.ui.screens.homescreen.MainScreen
import com.minorproject.eventgaze.MainScreen
import com.minorproject.eventgaze.model.data.Event
import com.minorproject.eventgaze.ui.screens.colleges_screen.CollegeEventScreen
import com.minorproject.eventgaze.ui.screens.detailScreen.DetailScreen
import com.minorproject.eventgaze.ui.theme.EventGazeTheme


@Composable
fun rememberAppState(scaffoldState: ScaffoldState = rememberScaffoldState(),
                     navController: NavHostController = rememberNavController(),
                     snackbarManager: SnackbarManager = SnackbarManager,
                     resources: Resources = resources(),
                     coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState,
    navController,snackbarManager,resources,coroutineScope
) {
    EventGazeAppState(scaffoldState,navController,snackbarManager, resources,coroutineScope)
}
@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@OptIn(ExperimentalSharedTransitionApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.ecommerceGraph(appState: EventGazeAppState){

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
        MainScreen(navigate = {route -> appState.clearAndNavigate(route)},
            detailnavigate = {route -> appState.navigate(route)})
    }
    composable("$DetailScreen/{eventId}",
        arguments = listOf(navArgument("eventId"){type = NavType.IntType})
    ){backStackEntry ->
        val eventId = backStackEntry.arguments?.getInt("eventId")
        DetailScreen(eventId = eventId)
    }
    composable("$CollegeEventScreen/{collegeId}",
        arguments = listOf(navArgument("collegeId"){type = NavType.IntType})
    ) { backStackEntry ->
        val collegeId = backStackEntry.arguments?.getInt("collegeId")
        CollegeEventScreen(collegeId = collegeId, detailnavigate = {route -> appState.navigate(route)})
    }
}




@ExperimentalSharedTransitionApi
@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun EventGazeApp(modifier: Modifier = Modifier.verticalScroll(rememberScrollState())) {
    EventGazeTheme {

        Surface(color = androidx.compose.material.MaterialTheme.colors.background) {
            val appState = rememberAppState()

            androidx.compose.material.Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = androidx.compose.material.MaterialTheme.colors.onPrimary
                            )
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                SharedTransitionScope {
                    NavHost(
                        navController = appState.navController,
                        startDestination = SplashScreen,
                        modifier = Modifier.padding(innerPaddingModifier)
                    ) {
                        ecommerceGraph(appState)
                    }
                }
            }
        }
    }
}