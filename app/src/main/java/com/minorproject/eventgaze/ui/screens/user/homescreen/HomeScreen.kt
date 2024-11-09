@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class)

package com.minorproject.eventgaze.ui.screens.user.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showSystemUi = true,)
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContentPreview() {

    // Mock values for testing the composable
//   val sampleEvent = Event(eventId = "sljlskjdfflc",// Replace with your own drawable resource
//    eventCategory =  2L,
//    eventName =    "Event Title",
//    eventDescription = "This is a sample description of the event. This is a sample description of the event." +
//            "This is a sample description of the event. This is a sample description of the event. This is a sample description of the event.",
//publisherId = 101,
//       eventTags = "sldlkd, odldl",
//       eventScope = "sdodldlc",
//       eventArt = "dlldfl"
//   )

   //  Use AnimatedVisibility to provide the AnimatedVisibilityScope
   EventGazeTheme {
       AnimatedVisibility(
           visible = true, // Set to true to make it visible in the preview
       ) {
//           HomeScreenContent(event = listOf(sampleEvent), onItemClick = { sampleEvent->
//               sampleEvent
//           }, animatedVisibilityScope = this,
//               onShareClick = {},
//               refresh = {},
//               category = listOf(Category(1L, "Sports"))
//           )
       }
   }
}


@Composable
fun HomeScreenContent(
    event: List<Event>,
    category: List<EventCategory>,
    modifier: Modifier = Modifier,
    onItemClick: (String?) -> Unit,
    onShareClick: (Event) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    refresh: ()-> Unit

) {
    var isRefreshing by remember { mutableStateOf(false) }
    val hazeState = remember { HazeState() }

    var selectedCategoryId by remember { mutableLongStateOf(0L) }

    val scope = rememberCoroutineScope()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            scope.launch {
                isRefreshing = true
                delay(3000)
                refresh()
                isRefreshing = false
            }
        }
    ) {

        LazyColumn(modifier.haze(
        hazeState,
        backgroundColor = MaterialTheme.colorScheme.background,
        tint = Color.Black.copy(alpha = .2f),
        blurRadius = 30.dp,
    )
//            .background(//color = MaterialTheme.colorScheme.onPrimary
//        brush = Brush.linearGradient(colors = listOf( MaterialTheme.colorScheme.primary.copy(.2f),
//           MaterialTheme.colorScheme.onPrimary.copy(.2f),
//            MaterialTheme.colorScheme.primary.copy(.2f)),
//             start = Offset(x=0f,y=100f), end = Offset(x = 800f, y = 1500f))
//    )
        ) {


        item {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 16.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.discover), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary)
            }
        }



        item {
            CategoryRow(categories = category, selectedCategoryId =  selectedCategoryId, onCategorySelected = {selectedCategoryId = it},modifier= modifier.hazeChild(hazeState))

        }
        item {
            Spacer(modifier.height(20.dp))
        }


        EventList(events = if (selectedCategoryId == 0L )event else event.filter { it.eventCategory.eventCategoryId == selectedCategoryId },

            modifier = Modifier, onItemClick = onItemClick ,
            onShareClick = onShareClick,
            animatedVisibilityScope = animatedVisibilityScope )



    }}


}
@Composable
fun CategoryRow(
    categories: List<EventCategory>,
    selectedCategoryId: Long,
    modifier: Modifier = Modifier,
    onCategorySelected: (Long) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(categories) {index, category ->
            if (category != null) {
                val isSelected = category.eventCategoryId == selectedCategoryId
                Button(
                    onClick = {

                        onCategorySelected(category.eventCategoryId)
                    },
                    colors = ButtonDefaults.buttonColors(
                       containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(0.2f) else
                        MaterialTheme.colorScheme.secondary.copy(
                            0.2f
                        )
                    ),
//                    border = BorderStroke(
//                        width = 0.5.dp,
//                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary.copy(
//                            0.2f
//                        )
//                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = category.eventName ?: "Unknown",
                        modifier = Modifier.padding(2.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}




fun LazyListScope.EventList(
    events: List<Event>,
    onShareClick: (Event) -> Unit,
    modifier: Modifier,onItemClick: (String?)-> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,

){
        itemsIndexed(events){index,event->

            ItemCard(
                 id = event.eventId,
                 image = event.eventArt,
                 title = event.eventName,
                 des = event.eventDescription, modifier = modifier,
                 // profileimg = event.profileimg,
                 publishername = event.eventScope,
                 animatedVisibilityScope = animatedVisibilityScope,
                 onShareClick = {onShareClick(event)},
                 onItemClick = {onItemClick(event.eventId)}
             )

    }
}


@Composable
fun ItemCard(
    id: String?,
    image: String?,
    title: String,
    des: String,
    modifier: Modifier,
    onShareClick: () -> Unit,
    onItemClick:()-> Unit,
   // profileimg: Int,
    publishername: String,
    animatedVisibilityScope: AnimatedVisibilityScope

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
                val dominantColor = palette?.mutedSwatch?.rgb
                dominantColor?.let {
                    backgroundColor = Color(it)
                }
            }
        }
    }

    val lightenedColor = backgroundColor.copy()
    val gradientColor = Brush.linearGradient(
        colors = listOf(backgroundColor, lightenedColor),
        start = Offset(1000f, 800f),
        end = Offset(1000f, 300f) // Adjust as necessary for the gradient effect
    )
    SharedTransitionLayout {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(3 / 4f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = lightenedColor
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary.copy(.2f)),
            elevation = CardDefaults.elevatedCardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier
                    //.background(MaterialTheme.colorScheme.secondary.copy(.1f))

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
                            .clickable { onItemClick() },
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .background(Color.Transparent)

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )

                        Text(
                            text = "24.11.2024",
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(start = 4.dp),
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = des,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = publishername,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Row {
                            IconButton(onClick = {isSaveClicked = !isSaveClicked}) {
                                Icon(
                                    imageVector = if (isSaveClicked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Save",
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }

                            IconButton(onClick = onShareClick) {
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

}
