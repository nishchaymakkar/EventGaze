package com.minorproject.eventgaze.ui.screens.user.colleges_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.College
import com.minorproject.eventgaze.model.data.colleges
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.CollegesScreen(
    onCollegeClick: (College, String) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {


    SharedTransitionLayout {
        Column(Modifier.fillMaxSize()) {
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.colleges),
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                itemsIndexed(colleges) { index, college ->

                    val collegeNo = "College No.$index"
                    Card(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)
                            .clickable { onCollegeClick(college, collegeNo) },
                       // elevation = CardDefaults.cardElevation(3.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(

                            containerColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize().padding(8.dp)

                        ) {
                            Row{
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Card(
                                        shape = MaterialTheme.shapes.medium,
                                        modifier = Modifier
                                        ,
                                    ) {
                                        Image(
                                            painter = painterResource(college.collegeImg),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp).aspectRatio(1/1f).sharedElement(
                                                state = rememberSharedContentState(key = "college_${college.collegeId}"), // Unique key
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                boundsTransform = { initial, target ->
                                                    tween(durationMillis = 3000)
                                                },
                                            )
                                        )
                                    }

                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Text(
                                            text = college.collegeName,
                                            style = MaterialTheme.typography.titleMedium,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            color = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.sharedElement(
                                                state = rememberSharedContentState(key = "college_${college.collegeName}"),
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                boundsTransform = { initial, target ->
                                                    tween(durationMillis = 3000)
                                                },
                                            )
                                        )
                                    }
                                }
                            }




                        }
                    }
                    Divider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}
