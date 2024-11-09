@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.minorproject.eventgaze.ui.screens.publisher.homescreen

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambdaNInstance
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.ui.screens.user.homescreen.ErrorScreen
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import com.minorproject.eventgaze.ui.screens.user.homescreen.ItemCard
import com.minorproject.eventgaze.ui.screens.user.homescreen.ShimmerListItem
import com.minorproject.eventgaze.ui.theme.EventGazeTheme

@ExperimentalSharedTransitionApi
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    EventGazeTheme {
       // HomeScreen(navigate = {}, eventU)
    }
}
@ExperimentalSharedTransitionApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit,
    eventUiState: EventUiState,
    retryAction: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    when (eventUiState){
        is EventUiState.Loading -> ShimmerListItem(isLoading = true)
        is EventUiState.Error -> {
            println("Error: ${eventUiState.error}")
            ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
        }
        is EventUiState.Success -> {
            println("success: ${eventUiState.event}")
            HomeScreenContentPublisher(

            events = eventUiState.event,
                navigate = navigate,
            animatedVisibilityScope = animatedVisibilityScope)
        }
    }






}

@Composable
fun HomeScreenContentPublisher(animatedVisibilityScope: AnimatedVisibilityScope, events: List<Event>,modifier: Modifier = Modifier, navigate: (String) -> Unit,
                               viewModel: HomeScreenPublisherViewModel = hiltViewModel()) {
    Box(modifier = modifier.fillMaxSize().padding(top = 30.dp).background(color = MaterialTheme.colorScheme.onPrimary)){


        EventList(events = events, modifier = Modifier, animatedVisibilityScope = animatedVisibilityScope, onItemClick = {} )

        Row(
            modifier
                .fillMaxWidth().align(Alignment.TopCenter).background(color = MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = stringResource(R.string.publishedevents),
                style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary,

                )
            Spacer(modifier.weight(1f))
            Icon(imageVector = Icons.Default.PersonOutline, tint = MaterialTheme.colorScheme.secondary,
                modifier = modifier.size(35.dp).weight(1f)
                    .clickable(onClick = { viewModel.onProfileClick(navigate)}),contentDescription = null)

        }
        FloatingActionButton(onClick = { viewModel.onAddEventClick(navigate)},modifier.align(Alignment.BottomEnd).padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary) {
            Icon(imageVector = Icons.Default.EventNote, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            //Text("Publish", modifier = modifier.padding(paddingValues = PaddingValues(0.dp)))
        }
    }

}

@ExperimentalSharedTransitionApi
@Composable
fun EventList(
    events: List<Event>,
    modifier: Modifier,
    onItemClick: ()-> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
){
val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.padding(top = 70.dp, bottom= 8.dp)) {
        itemsIndexed(events){ _, event->

            ItemCard(
                id = event.eventId,
                image = event.eventArt,
                title = event.eventName,
                des = event.eventDescription,
                modifier = modifier.clickable { onItemClick(/*event*/) },
               //profileimg = R.drawable.img,
                publishername = event.eventScope,
              animatedVisibilityScope = animatedVisibilityScope,
                onItemClick = {},
                onShareClick = {},
            )

        }

    }

}

