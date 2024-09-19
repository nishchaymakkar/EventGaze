package com.minorproject.eventgaze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.minorproject.eventgaze.ui.EventGazeApp
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

