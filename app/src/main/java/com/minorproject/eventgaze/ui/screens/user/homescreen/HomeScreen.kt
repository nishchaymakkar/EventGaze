@file:OptIn(ExperimentalSharedTransitionApi::class, ExperimentalSharedTransitionApi::class,
)

package com.minorproject.eventgaze.ui.screens.user.homescreen


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
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
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.Event
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.modal.data.Publishers
import com.minorproject.eventgaze.ui.screens.user.colleges_screen.isColorTooLight
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContentPreview() {

    // Mock values for testing the composable
   val sampleEvent = Event(eventId = "sljlskjdfflc",// Replace with your own drawable resource
    eventCategory =  EventCategory(1L,"Sports"),
    eventName =    "Event Title",
    eventDescription = "This is a sample description of the event. This is a sample description of the event." +
            "This is a sample description of the event. This is a sample description of the event. This is a sample description of the event.",
publisher= Publishers(publisherId = 1L,"","") ,
       eventTags = "sldlkd, odldl",
       college =  College("IIT Selampur",1L,""),
       eventArt = "dlldfl",
       eventVenue = ""
   )

   //  Use AnimatedVisibility to provide the AnimatedVisibilityScope
   EventGazeTheme {
       AnimatedVisibility(
           visible = true, // Set to true to make it visible in the preview
       ) {
           HomeScreenContent(event = listOf(sampleEvent,sampleEvent,sampleEvent), onItemClick = { sampleEvent->
               sampleEvent
           }, animatedVisibilityScope = this,
               onShareClick = {},
               refresh = {},
               category = listOf(EventCategory(1L, "Sports")),
               isloading = true
           )
       }
   }
}


@Composable
fun HomeScreenContent(
    event: List<Event>,
    category: List<EventCategory>,
    modifier: Modifier = Modifier,
    onItemClick: (Event) -> Unit,
    onShareClick: (Event) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    refresh: ()-> Unit,
    isloading: Boolean

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

       Box(
           modifier
               .fillMaxSize()
               .background(Color.Transparent)
//                .haze(
//                    hazeState,
//                    backgroundColor = MaterialTheme.colorScheme.background,
//                    tint = Color.Black.copy(alpha = .2f),
//                    blurRadius = 30.dp,
//                )
        ) {


          Column(modifier = modifier
              .align(Alignment.BottomCenter).padding()
              .fillMaxWidth().background(Color.Transparent)) {
//              Spacer(
//                  modifier
//                      .fillMaxWidth()
//                      .height(30.dp)
//                      .background(Color.Transparent)
//                      .align(Alignment.Start))

              EventList(
                   events = if (selectedCategoryId == 0L) event else event.filter { it.eventCategory.categoryId == selectedCategoryId },
                   onItemClick = onItemClick,
                   onShareClick = onShareClick,
                   animatedVisibilityScope = animatedVisibilityScope,
                   modifier = Modifier,
                   )
                      }

           CategoryRow(categories = category, selectedCategoryId =  selectedCategoryId, onCategorySelected = {selectedCategoryId = it},modifier= modifier.align(Alignment.TopStart))










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
        modifier = modifier.padding(bottom = 16.dp)
            .background(
                Color.Transparent
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        MaterialTheme.colorScheme.onPrimary,
//                    ),
//                    startY = 0f,
//                    endY = 150f
//                )
            ).padding(start = 16.dp)
    ) {
        itemsIndexed(categories) {index, category ->
            if (category != null) {
                LaunchedEffect(selectedCategoryId) {
                    Log.d("CategorySelection", "Selected category ID: $selectedCategoryId")
                }

                val isSelected = category.categoryId == selectedCategoryId
                Button(
                    onClick = {

                        onCategorySelected(category.categoryId)
                    },
                    colors = ButtonDefaults.buttonColors(
                       containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(.2f) else
                        MaterialTheme.colorScheme.background
                    ),
//                    border = BorderStroke(
//                        width = 0.5.dp,
//                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary.copy(
//                            0.2f
//                        )
//                    ),
                    modifier = Modifier.padding(vertical = 8.dp),
                    shape = RoundedCornerShape(50.dp),
                ) {
                    Text(
                        text = category.categoryName ?: "Unknown",
                        modifier = Modifier.padding(horizontal = 2.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                     //   fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
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

        val pagerState = androidx.compose.foundation.pager.rememberPagerState(initialPage = 0, pageCount = { events.size })
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val event = events[page]
            val scaleAndAlpha = calculateScaleAndAlpha(page, pagerState)
         Box(
             modifier = modifier.fillMaxSize().padding(top = 48.dp)
         )   {
                ItemCard(
                    id = event.eventId,
                    image = event.eventArt,
                    title = event.eventName,
                    des = event.eventDescription,
                    modifier = Modifier,
                    publishername = "",
                    onShareClick = { onShareClick(event) },
                    onItemClick = { onItemClick(event) },
                    animatedVisibilityScope = animatedVisibilityScope,
                    scale = scaleAndAlpha.first,
                    alpha = scaleAndAlpha.second
                )
            }
        }}







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
    animatedVisibilityScope: AnimatedVisibilityScope,
    alpha: Float,
    scale: Float,

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

    SharedTransitionLayout {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .scale(scale)
                .alpha(alpha)
                .fillMaxHeight()
                // .aspectRatio(3 / 4f)
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(2f)
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
                        }
                        Spacer(modifier.width(24.dp))
                        Row(
                           horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f)
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

                            IconButton(onClick = onShareClick,
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




@Composable
fun calculateScaleAndAlpha(page: Int, pagerState: PagerState): Pair<Float, Float> {
    val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
    val scale = 1f - 0.15f * kotlin.math.abs(pageOffset)
    val alpha = 1f - 0.25f * kotlin.math.abs(pageOffset)
    return scale.coerceIn(0.75f, 0.9f) to alpha.coerceIn(0.5f, 1f)
}
