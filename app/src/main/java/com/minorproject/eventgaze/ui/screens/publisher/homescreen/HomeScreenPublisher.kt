@file:OptIn(ExperimentalMaterial3Api::class)

package com.minorproject.eventgaze.ui.screens.publisher.homescreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.ui.screens.user.homescreen.ErrorScreen
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import com.minorproject.eventgaze.ui.screens.user.homescreen.ItemCard
import com.minorproject.eventgaze.ui.screens.user.homescreen.calculateScaleAndAlpha
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
    HomeScreenContentPublisher(


        navigate = navigate,
        animatedVisibilityScope = animatedVisibilityScope,
        eventUiState = eventUiState,
        retryAction = retryAction)







}

@Composable
fun HomeScreenContentPublisher(animatedVisibilityScope: AnimatedVisibilityScope,
                              modifier: Modifier = Modifier,
                               navigate: (String) -> Unit,
                               eventUiState: EventUiState,
                               retryAction: () -> Unit,
                               viewModel: HomeScreenPublisherViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        TopAppBar(
            colors =TopAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary,
                scrolledContainerColor = Color.Transparent,
                navigationIconContentColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.secondary,
                actionIconContentColor = Color.Unspecified
            ),
            title = {   Text(text = stringResource(R.string.publishedevents),
                style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.secondary,) },
            actions = { Icon(imageVector = Icons.Default.AccountCircle, tint = MaterialTheme.colorScheme.secondary,
                modifier = modifier.size(30.dp)
                    .clickable(onClick = { viewModel.onProfileClick(navigate)})
                ,contentDescription = null)
            }
        )
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddEventClick(navigate)},modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary) {
                Icon(imageVector = Icons.Default.EventNote, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                //Text("Publish", modifier = modifier.padding(paddingValues = PaddingValues(0.dp)))
            }

        }
        ) { innerPadding ->
        when (eventUiState){
            is EventUiState.Loading -> CircularProgressIndicator()
            is EventUiState.Error -> {
                println("Error: ${eventUiState.error}")
                ErrorScreen(retryAction = retryAction, modifier = Modifier.fillMaxSize())
            }
            is EventUiState.Success -> {
                println("success: ${eventUiState.event}")
                Box(modifier = modifier.fillMaxSize().padding(innerPadding).background(color = MaterialTheme.colorScheme.onPrimary)){


                    EventList(events = eventUiState.event, modifier = Modifier, animatedVisibilityScope = animatedVisibilityScope, onItemClick = {}, onShareClick = {} )



                }

            }
        }


    }


}

@Composable
fun EventList(
    events: List<Event>,
    onShareClick: (Event) -> Unit,
    modifier: Modifier, onItemClick: (Event)-> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
){




    val pagerState = rememberPagerState(initialPage = 0, pageCount = { events.size })
    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val event = events[page]
        val scaleAndAlpha = calculateScaleAndAlpha(page, pagerState)
        Box(
            modifier = modifier.fillMaxSize().padding(top = 32.dp)
        )   {
            ItemCard(
                id = event.eventId,
                image = event.eventArt ?: "",
                title = event.eventName ?: "",
                des = event.eventDescription ?: "",
                modifier = Modifier,
                publishername = event.publishers?.publisherOrgName ?: "",
                onShareClick = { onShareClick(event) },
                onItemClick = { onItemClick(event) },
                animatedVisibilityScope = animatedVisibilityScope,
                scale = scaleAndAlpha.first,
                alpha = scaleAndAlpha.second
            )
        }
    }
}


