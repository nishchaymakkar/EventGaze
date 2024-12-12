@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)

package com.minorproject.eventgaze.ui.screens.user.colleges_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventList
import com.minorproject.eventgaze.ui.screens.user.homescreen.ErrorScreen
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventUiState
import com.minorproject.eventgaze.ui.screens.user.homescreen.shareEvent

@Preview
@Composable
private fun CollegeEventScreenPreview() {
//    CollegeEventScreen(
//        collegeId = 1,
//        detailnavigate = {}
//    )
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CollegeEventScreen(
    college: College,
    popUp: ()-> Unit,
    eventUiState: EventUiState,
    navigate: (String) -> Unit,
    viewModel: CollegeEventViewModel = viewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val context = LocalContext.current
    val onShareClick: (Event) -> Unit = { event ->
        val shareLink = viewModel.getShareableLink(event)
        shareEvent(context,shareLink)
    }
    val textOffset = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition()

    // Animate the offset
    LaunchedEffect(Unit) {
        val textWidth = college.collegeName.length   // Approximation for width (adjust as needed)
        while (true) {

            textOffset.animateTo(
                targetValue = -textWidth.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (textWidth / 20f * 1000).toInt(),
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
            textOffset.snapTo(0f) // Reset offset for seamless looping
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (modifier = Modifier.padding(start = 20.dp, end = 8.dp)){
                    Text(
                        text = college.collegeName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp)
                            .offset(textOffset.value.dp)

                    )


                }},
                navigationIcon = {
                    IconButton(
                        onClick = {  popUp()},
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.onPrimary),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )
        {

            if (college != null) {


                item {
                    SharedTransitionLayout {
                        Box(
                            modifier = Modifier
                        ) {
                            Card(
                                shape = MaterialTheme.shapes.extraLarge
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.iitselampur),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .aspectRatio(1f)
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






                        }

                    }
                }
                when (eventUiState) {
                    is EventUiState.Loading -> item { CircularProgressIndicator() }
                    is EventUiState.Success ->
                        EventList(events = eventUiState.event, college = college,
                            onShareClick = { event ->
                               onShareClick(event)

                            }, onDeleteClick = {}, onItemClick = {event ->
                              onShareClick(event)
                            },
                            animatedVisibilityScope = animatedVisibilityScope, modifier = Modifier
                        )

                    is EventUiState.Error -> item {
                        ErrorScreen(modifier = Modifier, retryAction = {})
                    }


                }


            } else {

                item { Text(text = "College not found") }

            }
        }
    }

}

fun LazyListScope.EventList(
    events: List<Event>,
    college: College,
    onShareClick: (Event) -> Unit,
    modifier: Modifier, onItemClick: (Event)-> Unit,
    onDeleteClick: (Long) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
){





        itemsIndexed( events.filter { event -> event.college.collegeId == college.collegeId  }) { index, event ->


            Box(
                modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
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
                   // onDeleteClick = { onDeleteClick(event.eventId) },
                    animatedVisibilityScope = animatedVisibilityScope,

                    )

        }
    }
}


fun isColorTooLight(color: Int): Boolean {
    val red = (color shr 16) and 0xFF
    val green = (color shr 8) and 0xFF
    val blue = color and 0xFF
    // Calculate the perceived brightness of the color
    val brightness = (0.299 * red + 0.587 * green + 0.114 * blue) / 255
    return brightness > 0.8 // Threshold for "too light" colors
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
  //  onDeleteClick: () -> Unit,
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
        androidx.compose.material3.Card(
            modifier = modifier
                .fillMaxWidth()
//                .scale(scale)
//                .alpha(alpha)
                .fillMaxHeight()
                .aspectRatio(9 / 14f),
            colors = CardDefaults.cardColors(
                containerColor = lightenedColor
            ),
            onClick = { onItemClick() },
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
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(4f)
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(2f)
                    ) {
                        IconButton(onClick = {isSaveClicked = !isSaveClicked},
                            modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary.copy(.2f))) {
                            Icon(
                                imageVector = if (isSaveClicked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.secondary,

                                )
                        }

                        IconButton(onClick = {onShareClick()},
                            modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary.copy(.2f))) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = MaterialTheme.colorScheme.secondary
                            )

                        }
                    }



                }
            }
        }
    }
}