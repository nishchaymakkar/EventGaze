package com.minorproject.eventgaze

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.minorproject.eventgaze.ui.EventGazeApp
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
}

