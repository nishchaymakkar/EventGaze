@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package com.minorproject.eventgaze.ui.screens.publisher.homescreen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.ui.FAB_EXPLODE_BOUNDS_KEY
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.isColorTooLight
import com.minorproject.eventgaze.ui.screens.user.homescreen.ErrorScreen
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import com.minorproject.eventgaze.ui.screens.user.homescreen.ItemCard
import com.minorproject.eventgaze.ui.screens.user.homescreen.calculateScaleAndAlpha
import com.minorproject.eventgaze.ui.screens.user.homescreen.shareEvent
import com.minorproject.eventgaze.ui.theme.EventGazeTheme

@ExperimentalSharedTransitionApi
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {

    val eventUiState = EventUiState.Success(
        listOf(
            Event(
                eventId = 0L,
                eventCategory = EventCategory(1,"Tech"),
                eventName = "",
                eventDescription = "",
                publishers = null,
                eventTags = "",
                college = College("IIt Selampur",22,"Pakistan Lite","https://images.app.goo.gl/5CrM4HLxyhTrfkoz9"),
                eventArt = "",
                eventVenue = ""
            )
        )
    )
    EventGazeTheme {
        AnimatedVisibility(
            visible = true
        ){
           // HomeScreen(navigate = {}, eventUiState = eventUiState, retryAction = {}, animatedVisibilityScope = this)

        }
            }
}
@ExperimentalSharedTransitionApi
@Composable
fun SharedTransitionScope.HomeScreen(
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
fun SharedTransitionScope.HomeScreenContentPublisher(animatedVisibilityScope: AnimatedVisibilityScope,
                              modifier: Modifier = Modifier,
                               navigate: (String) -> Unit,
                               eventUiState: EventUiState,
                               retryAction: () -> Unit,
                               viewModel: HomeScreenPublisherViewModel = hiltViewModel()) {


    val deleteEventState by viewModel.deleteEventState.collectAsState()
    val context = LocalContext.current
    val onShareClick: (Event) -> Unit = { event ->
        val shareLink = viewModel.getShareableLink(event)

        shareEvent(context,shareLink)
    }
    LaunchedEffect(deleteEventState) {
        deleteEventState?.let {
            if (it.isSuccess) {
                Toast.makeText(
                    context,
                    it.exceptionOrNull()?.message ?: " Event Deleted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    it.exceptionOrNull()?.message ?: "Failed to delete event",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
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
            actions = {
                Icon(imageVector = Icons.Default.Person,
                    tint = MaterialTheme.colorScheme.secondary,
                modifier = modifier
                    .padding(10.dp)
                    .clickable(onClick = { viewModel.onProfileClick(navigate) })
                ,contentDescription = null)
            }
        )
    },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddEventClick(navigate)},modifier.padding(16.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = FAB_EXPLODE_BOUNDS_KEY
                    ),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
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
                Box(modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = MaterialTheme.colorScheme.onPrimary)){


                    EventList(events = eventUiState.event,
                        modifier = Modifier,
                        animatedVisibilityScope = animatedVisibilityScope,
                        onItemClick = {}, onShareClick = {event ->
                            onShareClick(event) }, onDeleteClick = { eventId ->
                            Log.d("on delete ", "$eventId")
                           // viewModel.onDeleteClick(eventId)

                        } )



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
    onDeleteClick: (Long) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
){





    LazyColumn(modifier = modifier.padding(horizontal = 16.dp,), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        itemsIndexed( events) { index, event ->


        Box(
            modifier = modifier.fillMaxSize()
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
                onDeleteClick = {onDeleteClick(event.eventId)},
                animatedVisibilityScope = animatedVisibilityScope,

            )
        }
        }
    }
}


@Composable
private fun ItemCard(
    id: Long?,
    image: String?,
    title: String,
    des: String,
    modifier: Modifier,
    onShareClick: () -> Unit,
    onItemClick:()-> Unit,
    // profileimg: Int,
    publishername: String,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onDeleteClick: () -> Unit,
//    alpha: Float,
//    scale: Float,

    ) {

    var isSaveClicked by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }
    val context = LocalContext.current

    LaunchedEffect(image) {
        //    Load image as a Bitmap
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(image)
            .allowHardware(false)
            .build()

        val result = (imageLoader.execute(request) as? SuccessResult)?.drawable
        val bitmap = (result?.toBitmap())

        bitmap?.let {
            // Generate palette from the bitmap
            Palette.from(it).generate { palette ->
                val dominantColor = palette?.dominantSwatch?.rgb
                dominantColor?.let {
                    // Check if the dominant color is too light
                    if (isColorTooLight(it)) {
                        backgroundColor = Color(0xFF333333) // Fallback dark color
                    } else {
                        backgroundColor = Color(it)
                    }
                }
            }
        }
    }

    val lightenedColor = backgroundColor
    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by remember { mutableStateOf(false) }
    SharedTransitionLayout {
        Card(
            modifier = modifier
                .fillMaxWidth()
//                .scale(scale)
//                .alpha(alpha)
                .fillMaxHeight()
                .aspectRatio(9 / 14f)
            ,
            colors = CardDefaults.cardColors(
                containerColor = lightenedColor
            ),
            onClick = {onItemClick()},
            // border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary.copy(.2f)),
            elevation = CardDefaults.elevatedCardElevation(2.dp)
        ) {


            Box(modifier) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_connection_error),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .aspectRatio(1 / 1f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .sharedElement(
                            state = rememberSharedContentState(key = "eventart$image"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initial, target ->
                                tween(durationMillis = 3000)
                            }
                        ),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxHeight()
                    .background(lightenedColor)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = des,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 3,
                        overflow = TextOverflow.Clip,
                        textAlign = TextAlign.Justify
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    //horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(4f)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = publishername ?: "Unknown", maxLines = 1,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier.width(30.dp))

                    }
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu Item",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = modifier
                            .clickable(onClick = { isSheetOpen = true })
                            .padding(
                                PaddingValues(0.dp)
                            )
                    )




                }
            }
        }
    }
    if (isSheetOpen){
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            sheetState = bottomSheetState,
            onDismissRequest = {
                isSheetOpen = false
            },
        ) {
            Column (
                modifier.fillMaxWidth()
            ){
                Row(
                    modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {isSaveClicked = !isSaveClicked},
                            modifier
                                .weight(1f).padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.secondary.copy(.2f))) {
                            Icon(
                                imageVector = if (isSaveClicked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.secondary,

                                )
                        }

                        IconButton(onClick = onShareClick,
                            modifier
                                .weight(1f).padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.secondary.copy(.2f))) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.secondary
                            )



                    }

                }
                Row(
                    modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {onDeleteClick()},
                        modifier
                            .weight(1f).padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.secondary.copy(.2f))) {
                        Icon(
                            imageVector =  Icons.Default.Delete ,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.secondary,

                            )
                    }

                    IconButton(onClick = onShareClick,
                        modifier
                            .weight(1f).padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.secondary.copy(.2f))) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.secondary
                        )



                    }

                }
            }
        }
    }
}