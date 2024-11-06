@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.minorproject.eventgaze.ui.screens.user.detailScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.google.android.material.resources.MaterialAttributes
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import kotlinx.coroutines.Dispatchers

@Preview
@Composable
private fun DetailScreenPreview() {
    EventGazeTheme {
        AnimatedVisibility(
            visible = true
        ) {
//            DetailScreen(
//                eventId = null,
//                animatedVisibilityScope = this,
//
//            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreen(
   eventId: String?,
    popUp: ()-> Unit,
   // event: Event?,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {

    Column(modifier.fillMaxWidth()){


        // Call the function to fetch the event using the provided eventId
        LaunchedEffect(eventId) {
            viewModel.getEventById(eventId)
        }

        // Observe the eventDetailUiState from the ViewModel
        val eventDetailUiState = viewModel.eventDetailUiState

        // Create UI based on the observed state
        when (eventDetailUiState) {
            is EventDetailUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is EventDetailUiState.Success -> {
                val event = eventDetailUiState.event
                SharedTransitionLayout {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState())
                                .background(MaterialTheme.colorScheme.onPrimary),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(event.eventArt)
                                    .crossfade(true).dispatcher(dispatcher = Dispatchers.IO) // Enable caching
                                    .build(),
                                placeholder = painterResource(R.drawable.loading_img),
                                error = painterResource(R.drawable.ic_connection_error),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1 / 1f)
                                    .clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                                    .align(Alignment.Start)
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "event id ${event.eventId}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ -> tween(durationMillis = 1000) }
                                    )
                            )
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
                            ) {
                                Text(
                                    text = event.eventName,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.sharedElement(
                                        state = rememberSharedContentState(key = event.eventName),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ -> tween(durationMillis = 1000) }
                                    )
                                )
                            }
                            Divider(color = MaterialTheme.colorScheme.tertiary)
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Column (horizontalAlignment = Alignment.End) {
                                    Text(text = "24.11.2024", color = MaterialTheme.colorScheme.secondary,

                                        style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Thin
                                        )
                                    Text(text = "12:20 P.M", color = MaterialTheme.colorScheme.secondary,
                                        style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Thin)
                                }
                            }
                            Divider(color = MaterialTheme.colorScheme.tertiary)
                            Row(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = event.eventDescription,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    textAlign = TextAlign.Justify,
                                    modifier = Modifier.sharedElement(
                                        state = rememberSharedContentState(key = event.eventDescription),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ -> tween(durationMillis = 1000) }
                                    )
                                )
                            }
                            Divider(color = MaterialTheme.colorScheme.tertiary)
                            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                                Text(text = "#event #eventsGaze #eventtags",modifier.padding(16.dp), color = MaterialTheme.colorScheme.primary)
                            }
                            Divider(color = MaterialTheme.colorScheme.tertiary)
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.img),
                                    contentDescription = null,
                                    modifier = Modifier.sharedElement(
                                        state = rememberSharedContentState(key = event),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ -> tween(durationMillis = 1000) }
                                    ),
                                    contentScale = ContentScale.Fit
                                )
                                Text(
                                    text = event.eventScope,
                                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        IconButton(
                            onClick = {popUp()},
                            modifier = modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                               
                            )

                        }
                    }
                }

            }
            is EventDetailUiState.Error -> {
                val errorMessage = (eventDetailUiState).error
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.CenterHorizontally))
            }

        }
   }
}

@Preview
@Composable
private fun DetailScreenUiPreview() {

    EventGazeTheme {
        AnimatedVisibility(
            visible = true
        ) {
            //DetailScreenUi(animatedVisibilityScope = this)
        }
    }
}
//
//@Composable
//fun DetailScreenUi(animatedVisibilityScope: AnimatedVisibilityScope,modifier: Modifier = Modifier){
//    val event = Event(eventId = "sljlskjdfflc",// Replace with your own drawable resource
//        categoryId =  2,
//        eventName =    "Event Title",
//        eventDescription = "This is a sample description of the event. This is a sample description of the event." +
//                "This is a sample description of the event. This is a sample description of the event. This is a sample description of the event." +
//                "This is a sample description of the event. This is a sample description of the event. This is a sample description of the event."
//                 +"This is a sample description of the event. This is a sample description of the event. This is a sample description of the event." +
//                "This is a sample description of the event. This is a sample description of the event. This is a sample description of the event.",
//        publisherId = 101,
//        eventTags = "sldlkd, odldl",
//        eventScope = "sdodldlc",
//        eventImage = "dlldfl"
//    )
//    SharedTransitionLayout {
//        Box(modifier = Modifier.fillMaxSize()) {
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .verticalScroll(rememberScrollState())
//                    .background(MaterialTheme.colorScheme.onPrimary),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(event.eventImage)
//                        .crossfade(true).dispatcher(dispatcher = Dispatchers.IO) // Enable caching
//                        .build(),
//                    placeholder = painterResource(R.drawable.loading_img),
//                    error = painterResource(R.drawable.ic_connection_error),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .aspectRatio(1 / 1f)
//                        .clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
//                        .align(Alignment.Start)
//                        .sharedElement(
//                            state = rememberSharedContentState(key = event.eventId),
//                            animatedVisibilityScope = animatedVisibilityScope,
//                            boundsTransform = { _, _ -> tween(durationMillis = 1000) }
//                        )
//                )
//                Row(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
//                ) {
//                    Text(
//                        text = event.eventName,
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Medium,
//                        color = MaterialTheme.colorScheme.secondary,
//                        modifier = Modifier.sharedElement(
//                            state = rememberSharedContentState(key = event.eventName),
//                            animatedVisibilityScope = animatedVisibilityScope,
//                            boundsTransform = { _, _ -> tween(durationMillis = 1000) }
//                        )
//                    )
//                }
//                Divider(color = MaterialTheme.colorScheme.tertiary)
//                Row(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                   Column (horizontalAlignment = Alignment.End) {
//                        Text(text = "24.11.2024", color = MaterialTheme.colorScheme.secondary)
//                        Text(text = "12:20 P.M", color = MaterialTheme.colorScheme.secondary)
//                    }
//                }
//                Divider(color = MaterialTheme.colorScheme.tertiary)
//                Row(
//                    modifier = modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    Text(
//                        text = event.eventDescription,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.secondary,
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier.sharedElement(
//                            state = rememberSharedContentState(key = event.eventDescription),
//                            animatedVisibilityScope = animatedVisibilityScope,
//                            boundsTransform = { _, _ -> tween(durationMillis = 1000) }
//                        )
//                    )
//                }
//                Divider(color = MaterialTheme.colorScheme.tertiary)
//                Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
//                    Text(text = "#event #eventsGaze #eventtags",modifier.padding(16.dp), color = MaterialTheme.colorScheme.primary)
//                }
//                Divider(color = MaterialTheme.colorScheme.tertiary)
//                Row(
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = modifier
//                        .padding(8.dp)
//                        .fillMaxWidth(),
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.img),
//                        contentDescription = null,
//                        modifier = Modifier.sharedElement(
//                            state = rememberSharedContentState(key = event),
//                            animatedVisibilityScope = animatedVisibilityScope,
//                            boundsTransform = { _, _ -> tween(durationMillis = 1000) }
//                        ),
//                        contentScale = ContentScale.Fit
//                    )
//                    Text(
//                        text = event.eventScope,
//                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.secondary
//                    )
//                }
//            }
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = null,
//                tint = MaterialTheme.colorScheme.secondary,
//                modifier = modifier
//                    .align(Alignment.TopStart)
//                    .padding(16.dp)
//            )
//        }
//    }
//}