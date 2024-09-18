package com.minorproject.e_commerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api

import com.minorproject.e_commerce.ui.EcommerceApp
import com.minorproject.e_commerce.ui.theme.ECommerceTheme
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceTheme {
                EcommerceApp()
            }
        }
    }
}

