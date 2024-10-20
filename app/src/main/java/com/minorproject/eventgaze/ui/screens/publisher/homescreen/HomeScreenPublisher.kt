package com.minorproject.eventgaze.ui.screens.publisher.homescreen

import androidx.annotation.OpenForTesting
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person2
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusModifier
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.Event
import com.minorproject.eventgaze.model.data.categories
import com.minorproject.eventgaze.ui.screens.user.homescreen.CategoryRow
import com.minorproject.eventgaze.ui.screens.user.homescreen.EventList
import com.minorproject.eventgaze.ui.theme.EventGazeTheme

@ExperimentalSharedTransitionApi
@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    EventGazeTheme {
        HomeScreen(navigate = {})
    }
}
@ExperimentalSharedTransitionApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenPublisherViewModel = viewModel(),
    navigate: (String) -> Unit
) {

    Box(modifier = modifier.fillMaxSize()){
        Column(modifier.fillMaxSize()) {
            Spacer(modifier = modifier.padding(20.dp))
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(text = stringResource(R.string.publishedevents),
                    style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary,

                )
                Spacer(modifier.weight(1f))
                Icon(imageVector = Icons.Default.PersonOutline,
                    modifier = modifier.size(35.dp).weight(1f).clickable(onClick = {}),contentDescription = null)
            }
          //  EventList(events = events, modifier = Modifier, )

        }
        FloatingActionButton(onClick = {},modifier.align(Alignment.BottomEnd).padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null,modifier.clickable { viewModel.onProfileClick(navigate) })
            //Text("Publish", modifier = modifier.padding(paddingValues = PaddingValues(0.dp)))
        }
    }



}


//
//@ExperimentalSharedTransitionApi
//@Composable
//fun EventList(
//    events: List<Event>,
//    modifier: Modifier,//onItemClick: (Event,Int)-> Unit,
//    //animatedVisibilityScope: AnimatedVisibilityScope
//){
//
//    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.padding(bottom= 8.dp)) {
//        itemsIndexed(events){index,event->
//
//            ItemCard(
//                image = event.image, title = event.title,
//                des = event.des, modifier = modifier.clickable { /*onItemClick(event,index)*/ },
//                profileimg = event.profileimg,
//                publishername = event.publishername,
////                animatedVisibilityScope = animatedVisibilityScope
//            )
//
//        }
//
//    }
//
//}


//@ExperimentalSharedTransitionApi
//@Composable
//private fun ItemCard(
//    image: Int,
//    title: String,
//    des: String,
//    modifier: Modifier,
//    profileimg: Int,
//    publishername: String,
//   // animatedVisibilityScope: AnimatedVisibilityScope
//
//) {
//    var backgroundColor by remember { mutableStateOf(androidx.compose.ui.graphics.Color.Transparent) }
//    val context = LocalContext.current
//
//    LaunchedEffect(image) {
//        // Load image as a Bitmap
//        val imageLoader = ImageLoader(context)
//        val request = ImageRequest.Builder(context)
//            .data(image)
//            .allowHardware(false)
//            .build()
//
//        val result = (imageLoader.execute(request) as? SuccessResult)?.drawable
//        val bitmap = (result?.toBitmap())
//
//        bitmap?.let {
//            // Generate palette from the bitmap
//            Palette.from(it).generate { palette ->
//                val dominantColor = palette?.dominantSwatch?.rgb
//                dominantColor?.let {
//                    backgroundColor = Color(it)
//                }
//            }
//        }
//    }
//
//    val lightenedColor = backgroundColor.copy(alpha = 0.4f)
//    val gradientColor = Brush.linearGradient(
//        colors = listOf(backgroundColor, lightenedColor),
//        start = Offset(0f, 0f),
//        end = Offset(500f, 500f) // Adjust as necessary for the gradient effect
//    )
//
//    SharedTransitionLayout {
//        Card(modifier = modifier
//            .height(360.dp)
//            .fillMaxWidth()
//            .padding(start = 16.dp, end = 16.dp), colors = CardDefaults.cardColors(
//            containerColor = androidx.compose.ui.graphics.Color.Transparent
//        )) {
//            Column (
//                modifier.fillMaxSize().background(brush = gradientColor), verticalArrangement = Arrangement.SpaceBetween
//            ){
//                Image(painter = painterResource(image), contentDescription = null,modifier = Modifier/*.sharedElement(
//                    state = rememberSharedContentState(key = "event_${image}"),
//                    animatedVisibilityScope = animatedVisibilityScope,
//                    boundsTransform = { initial, target ->
//                        tween(durationMillis = 1000)
//                    },
//                )*/
//                    .fillMaxWidth().align(Alignment.Start)
//                    .height(200.dp)
//                    .padding(8.dp), contentScale = ContentScale.Crop )
//                Row(
//
//                ) {
//                    Text(text = title, style = MaterialTheme.typography.bodyLarge, color = androidx.compose.ui.graphics.Color.White,
//                        modifier = Modifier.padding(8.dp)/*.sharedElement(
//                            state = rememberSharedContentState(key = "event_${title}"),
//                            animatedVisibilityScope = animatedVisibilityScope,
//                            boundsTransform = { initial, target ->
//                                tween(durationMillis = 1000)
//                            },
//                        )*/)
//                }
//                Row {
//                    Text(
//                        text = des,
//                        style = MaterialTheme.typography.bodySmall,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.padding(8.dp)
////                            .sharedElement(
////                            state = rememberSharedContentState(key = "event_${des}"),
////                            animatedVisibilityScope = animatedVisibilityScope,
////                            boundsTransform = { initial, target ->
////                                tween(durationMillis = 1000)
////                            },
////                        )
//                        ,color = MaterialTheme.colorScheme.onPrimary
//                    )
//                }
//
//                Row(
//                    horizontalArrangement = Arrangement.Start, modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(profileimg),
//                        contentDescription = null,
//                        modifier = modifier,
//                        contentScale = ContentScale.Fit
//                    )
//                    Text(text = publishername,modifier = modifier.padding(start = 8.dp, end = 8.dp), color = MaterialTheme.colorScheme.onPrimary)
//                }
//            }
//        }
//    }
//
//
//}