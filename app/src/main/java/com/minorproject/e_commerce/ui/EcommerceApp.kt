@file:OptIn(ExperimentalMaterial3Api::class)

package com.minorproject.e_commerce.ui

import android.content.res.Resources
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
import com.minorproject.e_commerce.ECommerceAppState
import com.minorproject.e_commerce.HomeScreen
import com.minorproject.e_commerce.SignInScreen
import com.minorproject.e_commerce.SignUpScreen
import com.minorproject.e_commerce.SplashScreen
import com.minorproject.e_commerce.ui.common.components.SnackbarManager
import com.minorproject.e_commerce.ui.screens.loginscreen.SignInScreen
import com.minorproject.e_commerce.ui.screens.signupscreen.SignUpScreen
import com.minorproject.e_commerce.ui.screens.splashscreen.SplashScreen
import com.minorproject.e_commerce.ui.theme.ECommerceTheme
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minorproject.e_commerce.ui.screens.homescreen.MainScreen
import com.minorproject.e_commerce.MainScreen


@Composable
fun rememberAppState(scaffoldState: ScaffoldState = rememberScaffoldState(),
                     navController: NavHostController = rememberNavController(),
                     snackbarManager: SnackbarManager = SnackbarManager,
                     resources: Resources = resources(),
                     coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState,
    navController,snackbarManager,resources,coroutineScope
) {
    ECommerceAppState(scaffoldState,navController,snackbarManager, resources,coroutineScope)
}
@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.ecommerceGraph(appState: ECommerceAppState){
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
        MainScreen(navigate = {route -> appState.clearAndNavigate(route)})
    }
}




@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun EcommerceApp(modifier: Modifier = Modifier.verticalScroll(rememberScrollState())) {
    ECommerceTheme {

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