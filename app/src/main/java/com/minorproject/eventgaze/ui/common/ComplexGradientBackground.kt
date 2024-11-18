package com.minorproject.eventgaze.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Preview
@Composable
fun ComplexGradientBackground() {

    val hazeState by remember { mutableStateOf(HazeState()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set a clean dark background
    ) {
        // Create glowing circles with varied sizes and positions
        val circles = listOf(
            CircleData(400.dp, Offset(-100f, -200f)), // Large top-left
            CircleData(300.dp, Offset(300f, -150f)), // Medium top-right
            CircleData(250.dp, Offset(-150f, 200f)), // Medium center-left
            CircleData(150.dp, Offset(150f, 400f)), // Small bottom-center
            CircleData(200.dp, Offset(350f, 300f)),  // Medium bottom-right
            CircleData(400.dp, Offset(150f, 500f)), // Large top-left
            CircleData(150.dp, Offset(-50f, 500f)),  // Small bottom-left
            CircleData(100.dp, Offset(50f, 100f)),   // Small near top-center
            CircleData(120.dp, Offset(350f, 150f)),  // Small mid-right
            CircleData(80.dp, Offset(250f, 400f)),
            CircleData(200.dp, Offset(260f, 400f)), // Medium-large for balanced glow
            CircleData(180.dp, Offset(450f, -100f)),
            CircleData(150.dp, Offset(-50f, 500f)),  // Small bottom-left
            CircleData(100.dp, Offset(50f, 160f)),   // Small near top-center
            CircleData(120.dp, Offset(350f, 150f)),  // Small mid-right
            CircleData(80.dp, Offset(250f, 400f)),
            CircleData(400.dp, Offset(10f, 100f))
        )

        circles.forEach { circle ->
            Box(
                modifier = Modifier
                    .size(circle.size)
                    .offset(circle.offset.x.dp, circle.offset.y.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF00FF00).copy(alpha = 0.4f), // Bright green
                                Color(0xFF003300).copy(alpha = 0.2f), // Darker green
                                Color.Transparent // Fades to transparent
                            ),
                            radius = circle.size.value * 2
                        ),
                        shape = CircleShape
                    )
                    .blur(radius = circle.size / 2)
            )
        }

        // Add an optional subtle overlay for a polished look
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f), // Subtle dark overlay at the top
                            Color.Transparent // Fully transparent at the bottom
                        )
                    )
                )
        )
    }

}
// Circle data model for easy customization
data class CircleData(val size: Dp, val offset: Offset)
fun Modifier.meshGradient(color: Color): Modifier = composed{
    background(shape = CircleShape,
        brush = Brush.linearGradient(
          colors = listOf(
              color,
              color
          )
        )
    )
}
