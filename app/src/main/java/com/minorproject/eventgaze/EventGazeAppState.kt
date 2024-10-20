package com.minorproject.eventgaze

import android.content.res.Resources
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import com.minorproject.eventgaze.ui.common.components.SnackbarManager
import com.minorproject.eventgaze.ui.common.components.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class EventGazeAppState(
    val scaffoldHostState: SnackbarHostState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
){
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldHostState.showSnackbar(text)
                snackbarManager.clearSnackbarState()
            }
        }
    }
    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        Log.d("Navigation", "Navigating to $route")
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}