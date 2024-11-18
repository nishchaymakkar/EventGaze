package com.minorproject.eventgaze.ui.screens.user.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.minorproject.eventgaze.R
import kotlinx.coroutines.Dispatchers

@Composable
fun DetailScreenForLinks(
    modifier: Modifier = Modifier,
    popUp: ()-> Unit,
    eventId: String?,viewModel: DetailScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(eventId) {
        viewModel.getEventById(eventId)
    }

    // Observe the eventDetailUiState from the ViewModel
    val eventDetailUiState = viewModel.eventDetailUiState
Box(modifier.fillMaxSize()){
    when (eventDetailUiState) {
        is EventDetailUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        is EventDetailUiState.Success -> {
            val eventFromIntent = eventDetailUiState.event

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
                            .data(eventFromIntent.eventArt)
                            .crossfade(true)
                            .dispatcher(dispatcher = Dispatchers.IO) // Enable caching
                            .build(),
                        placeholder = painterResource(R.drawable.loading_img),
                        error = painterResource(R.drawable.ic_connection_error),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .aspectRatio(1 / 1f)
                            //.clip(shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                            .align(Alignment.Start)

                    )
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                bottom = 16.dp,
                                end = 16.dp
                            )
                    ) {
                        Text(
                            text = eventFromIntent.eventName,
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.secondary.copy(.2f))
                    Row (modifier.fillMaxWidth().padding(start =  16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp), verticalAlignment = Alignment.CenterVertically){
                        Icon(
                            imageVector = Icons.Default.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier.width(10.dp))
                        if (eventFromIntent != null) {
                            Text(
                                text = eventFromIntent.eventDate ?: "dd mm yyyy",
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Row (modifier.fillMaxWidth().padding(start =  16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp), verticalAlignment = Alignment.Top){
                        Icon(
                            imageVector = Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier.width(10.dp))
                        if (eventFromIntent != null) {
                            Text(
                                text = eventFromIntent.eventDescription,
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                    Divider(color = MaterialTheme.colorScheme.secondary.copy(.2f))
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = eventFromIntent.eventDescription,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                        )
                    }


//                            Row(
//                                modifier
//                                    .fillMaxWidth()
//                                    .align(Alignment.End),
//                                horizontalArrangement = Arrangement.Start
//                            ) {
//                                Text(
//                                    text = event.eventTags, modifier.padding(16.dp),
//                                    color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyMedium
//                                )
//                            }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .padding(16.dp)
                            .align(Alignment.End)
                            .fillMaxWidth(),
                    ) {

                        Image(
                            painter = painterResource(R.drawable.img),
                            contentDescription = null,
                            modifier = Modifier,
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "",
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )

                    }


                }
                IconButton(
                    onClick = { popUp() },
                    modifier = modifier
                        .align(Alignment.TopStart)
                        .padding(top = 26.dp, start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                    )

                }


            }

        }
        is EventDetailUiState.Error -> {
            val errorMessage = (eventDetailUiState).error
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(
                Alignment.Center))
        }

    }
}
}
    // Create UI based on the observed state
