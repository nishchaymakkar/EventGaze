package com.minorproject.eventgaze.ui.screens.user.colleges_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.modal.data.College
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.CollegesScreen(
    onCollegeClick: (College, String) -> Unit,
    colleges: List<College> = emptyList(),
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val hazeState = remember { HazeState() }

    val lazyListState by remember { mutableStateOf(LazyListState()) }
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
        ),
        exit = fadeOut(),
        modifier = Modifier,

        ){

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onPrimary)
//                    .haze(
//                    hazeState,
//                    backgroundColor = MaterialTheme.colorScheme.background,
//                    tint = Color.Black.copy(alpha = .2f),
//                    blurRadius = 30.dp,
//                )
                    //.background(brush = Brush.linearGradient(colors = listOf( MaterialTheme.colorScheme.primary.copy(.2f),MaterialTheme.colorScheme.onPrimary), start = Offset(x=0f,y=100f), end = Offset(x = 800f, y = 1500f)))

                    .padding(horizontal =  16.dp),
                horizontalAlignment = Alignment.Start
            ) {


                itemsIndexed(colleges) { index, college ->

                    val collegeNo = "College No.$index"

                    SharedTransitionLayout {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth().animateEnterExit(
                                    enter = slideInVertically(
                                        animationSpec = spring(
                                            stiffness = StiffnessVeryLow,
                                            dampingRatio = DampingRatioLowBouncy
                                        ),
                                        initialOffsetY = { it * (index + 1) } // staggered entrance
                                    )
                                )
                                .padding(vertical = 8.dp)
                                .clickable { onCollegeClick(college, collegeNo) },
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
//                            border = BorderStroke(
//                                width = .5.dp,
//                                color = MaterialTheme.colorScheme.secondary.copy(.2f)
//                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    shape = MaterialTheme.shapes.medium,
                                    modifier = Modifier.size(60.dp),
                                    elevation = CardDefaults.cardElevation(2.dp),
                                ) {
//                                    Image(
//                                        painter = painterResource(college.collegeImage),
//                                        contentDescription = null,
//                                        contentScale = ContentScale.Crop,
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .sharedElement(
//                                                state = rememberSharedContentState(key = "college_${college.collegeId}"),
//                                                animatedVisibilityScope = animatedVisibilityScope,
//                                                boundsTransform = { initial, target ->
//                                                    tween(durationMillis = 500)
//                                                }
//                                            )
//                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    verticalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = college.collegeName,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.secondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
//                                            .sharedElement(
//                                            state = rememberSharedContentState(key = "college_${college.collegeName}"),
//                                            animatedVisibilityScope = animatedVisibilityScope,
//                                            boundsTransform = { initial, target ->
//                                                tween(durationMillis = 500)
//                                            }
//                                        )
                                    )
                                    Text(
                                        text = college.collegeAddress, // Add additional info like location if available
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.secondary.copy(.5f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        // Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary)
                    }    }

        }
    }
}
