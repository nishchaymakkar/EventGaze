package com.minorproject.eventgaze.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ComplexGradientBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF6200EA).copy(alpha = 0.7f),  // Deep Purple with transparency
                        Color.Transparent
                    ),
                    center = Offset(300f, 300f),
                    radius = 600f
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFAA00FF).copy(alpha = 0.6f),  // Pinkish-Purple with transparency
                            Color.Transparent
                        ),
                        center = Offset(700f, 600f),
                        radius = 700f
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFF4081).copy(alpha = 0.5f),  // Pink with transparency
                                Color.Transparent
                            ),
                            center = Offset(500f, 900f),
                            radius = 800f
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF0D0221),  // Dark Background
                                    Color.Transparent
                                ),
                                center = Offset(400f, 500f),
                                radius = 1000f
                            )
                        )
                )
            }
        }
    }
}
