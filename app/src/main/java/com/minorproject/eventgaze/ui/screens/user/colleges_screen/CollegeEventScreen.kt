package com.minorproject.eventgaze.ui.screens.user.colleges_screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.data.colleges

@Preview
@Composable
private fun CollegeEventScreenPreview() {
//    CollegeEventScreen(
//        collegeId = 1,
//        detailnavigate = {}
//    )
}
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CollegeEventScreen(
    collegeId: Int?,
    detailnavigate: (String) -> Unit,
    viewModel: CollegeEventViewModel = viewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.onPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val college = colleges.find { it.collegeId == collegeId }
        if (college != null) {
            item {
                SharedTransitionLayout (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box{
                        Card(
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Image(
                                painter = painterResource(college.collegeImg),
                                contentDescription = null,
                                modifier = Modifier.aspectRatio(1/1f)
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "college_${college.collegeId}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { initial, target ->
                                            tween(durationMillis = 3000)
                                        },
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }



                            Card(
                                shape = MaterialTheme.shapes.small,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                modifier = Modifier.blur(150.dp).fillMaxWidth().padding(10.dp).align(Alignment.BottomCenter)
                                    .fillMaxWidth(),
                            ){
                                Text(
                                    text = college.collegeName,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    maxLines = 2,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(10.dp)
                                        .background(color = MaterialTheme.colorScheme.background)
                                        .fillMaxWidth()
                                        .sharedElement(
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

//            EventList(
//                events = event,
//                detailnavigate = detailnavigate,
//                animatedVisibilityScope = animatedVisibilityScope,
//                viewModel = viewModel
//            )

        } else {
            item {
                Text(text = "College not found")
            }

        }
    }
}

@ExperimentalSharedTransitionApi
fun LazyListScope.EventList(
    events: List<Event>,
    detailnavigate: (String) -> Unit,
    viewModel: CollegeEventViewModel,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

        itemsIndexed(events) { index, event ->
//            ItemCard(
//                id = event.id,
//                image = event.image,
//                title = event.title,
//                des = event.des,
//                modifier = Modifier.clickable { viewModel.onItemClick(event.id, detailnavigate) },
//                profileimg = event.profileimg,
//                publishername = event.publishername,
//                animatedVisibilityScope = animatedVisibilityScope
            //)

    }
}



