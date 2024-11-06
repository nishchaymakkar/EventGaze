package com.minorproject.eventgaze.ui.screens.user.homescreen

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ShimmerListItem(
    isLoading: Boolean = true,
    modifier: Modifier = Modifier
) {

    if(isLoading){
        LazyColumn(modifier.background(color = MaterialTheme.colorScheme.tertiary)) {
            item {
                Row(
                    modifier.padding(start = 16.dp, top = 40.dp, bottom = 10.dp)
                ) {
                    Box(modifier.width(150.dp).height(40.dp).shimmerEffect())
                }
            }
            item {
                CategoryRow()
            }
            items(10){
                Card (modifier.fillMaxWidth().padding(8.dp).aspectRatio(1/1f), colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )){
                    Box(modifier.fillMaxSize().shimmerEffect())
                }
            }
        }
    }
}

@Composable
fun CategoryRow(modifier: Modifier = Modifier) {
        LazyRow {
            items(10){
                Card(shape = RoundedCornerShape(50.dp),
                    modifier = modifier.padding(10.dp),colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )) {
                    Box(
                        modifier
                            .size(55.dp).shimmerEffect()
                            )
                }
            }
        }
}



fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
       mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition()
    val startOptionX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue =  2* size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.tertiary
        ),
            start =  Offset(startOptionX,0f),
            end = Offset(startOptionX + size.width.toFloat(),size.height.toFloat())
            )
    )
        .onGloballyPositioned {
            size = it.size
        }
}