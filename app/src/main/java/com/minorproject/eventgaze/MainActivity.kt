package com.minorproject.eventgaze

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.minorproject.eventgaze.ui.EventGazeApp
import com.minorproject.eventgaze.ui.screens.user.detailScreen.DetailScreen
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalSharedTransitionApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventGazeTheme {
                EventGazeApp()
            }
        }
    }

//    override fun onNewIntent(intent: Intent) {
//        super.onNewIntent(intent)
//        intent?.let {
//            handleIntent(it)
//        }
//    }
//
//    private fun handleIntent(intent: Intent?) {
//        intent?.data?.let { uri ->
//            // Extract the path or parameters from the URI
//            val eventId = uri.lastPathSegment // Should give "13" from /events/id/13
//            // Pass this to the required screen or ViewModel
//
//        }
//        }


}



