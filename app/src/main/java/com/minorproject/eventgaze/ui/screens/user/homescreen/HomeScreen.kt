@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.minorproject.eventgaze.ui.screens.user.homescreen


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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.Category
import com.minorproject.eventgaze.model.data.Event
import com.minorproject.eventgaze.model.data.categories

@Preview(showSystemUi = true, apiLevel = 34)
@ExperimentalMaterial3Api
@Composable
fun HomeScreenContentPreview() {

    // Mock values for testing the composable
   val sampleEvent = Event(1,// Replace with your own drawable resource
     2,
       "Event Title",
    "This is a sample description of the event. This is a sample description of the event." +
            "This is a sample description of the event. This is a sample description of the event. This is a sample description of the event.",

             "Publisher Name")
    val position = 1

    // Use AnimatedVisibility to provide the AnimatedVisibilityScope
    AnimatedVisibility(
        visible = true, // Set to true to make it visible in the preview
    ) {
       HomeScreenContent(event = listOf(sampleEvent) , onItemClick= { sampleEvent, _->
           sampleEvent
       } , animatedVisibilityScope = this )
    }
}


@Composable
fun HomeScreenContent(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onItemClick: (Event, Int) -> Unit,
   animatedVisibilityScope: AnimatedVisibilityScope
) {


    var selectedCategoryId by remember { mutableIntStateOf(1) }
   LazyColumn(modifier) {


    item {
        Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.discover), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary)
        }
    }



item {
    CategoryRow(categories = categories, selectedCategoryId = selectedCategoryId, onCategorySelected = {selectedCategoryId = it})

}
       item {
           Spacer(modifier.height(20.dp))
       }


            EventList(events = if (selectedCategoryId ==1 )event else event.filter { it.categoryid == selectedCategoryId }, selectedCategoryId = selectedCategoryId,modifier = Modifier, onItemClick = onItemClick , animatedVisibilityScope = animatedVisibilityScope )



        }
}

@Composable
fun CategoryRow(
    categories: List<Category>,
    selectedCategoryId: Int,
    onCategorySelected: (Int) -> Unit
){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(start=16.dp,end= 16.dp)) {
        items(categories){category->
            val isSelected = selectedCategoryId == category.id

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier.size(55.dp).clickable(onClick = {onCategorySelected(category.id)}),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary
                ),
                elevation = CardDefaults.cardElevation(2.dp)
                // border = BorderStroke(width = 1.dp, color = if (isSelected)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
            ) {
                Box(modifier = Modifier.fillMaxSize()){
                Icon(imageVector = category.icon,contentDescription = null,
                    modifier = Modifier.align(Alignment.Center))}
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = category.name, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis,
                color = if ( isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
        }
    }
}

fun LazyListScope.EventList(
    events: List<Event>, selectedCategoryId: Int,
    modifier: Modifier,onItemClick: (Event,Int)-> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
){
//    val filteredEvents = if (selectedCategoryId == 1 ) {
//        events
//    } else {
//        events.filter { it.categoryid == selectedCategoryId }
//    }
  //  LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        itemsIndexed(events){index,event->

            ItemCard(
                id = event.id,
                //image = event.image,
                title = event.title,
                des = event.des, modifier = modifier.clickable { onItemClick(event,index) },
               // profileimg = event.profileimg,
                publishername = event.publishername,
                animatedVisibilityScope = animatedVisibilityScope
            )

        //}
    }
}


@Composable
fun ItemCard(
    id: Int,
   // image: Int,
    title: String,
    des: String,
    modifier: Modifier,
   // profileimg: Int,
    publishername: String,
    animatedVisibilityScope: AnimatedVisibilityScope

) {
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }
    val context = LocalContext.current

 //   LaunchedEffect(image) {
        // Load image as a Bitmap
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
//        start = Offset(1000f, 800f),
//        end = Offset(1000f, 300f) // Adjust as necessary for the gradient effect
//    )

    SharedTransitionLayout {
        Card(modifier = modifier
            .height(360.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, top=8.dp, bottom = 8.dp, end = 16.dp),
            //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
            elevation = CardDefaults.elevatedCardElevation(2.dp),

        ) {
            Column (
                modifier.fillMaxSize().background(
                    
                    MaterialTheme.colorScheme.tertiary
                ), verticalArrangement = Arrangement.SpaceBetween
            ){
                Image(painter = painterResource(R.drawable.img_2), contentDescription = null,modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "event_img"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initial, target ->
                        tween(durationMillis = 1000)
                    },
                )
                    .fillMaxWidth().align(Alignment.Start)
                    .height(200.dp), contentScale = ContentScale.Crop )
                Row(

                ) {
                    Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(8.dp).sharedElement(
                            state = rememberSharedContentState(key = "event_${title}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initial, target ->
                                tween(durationMillis = 1000)
                            },
                        ))
                }
                Row {
                    Text(
                        text = des,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp).sharedElement(
                            state = rememberSharedContentState(key = "event_${des}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initial, target ->
                                tween(durationMillis = 1000)
                            },
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Start, modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.img),
                        contentDescription = null,
                        modifier = modifier,
                        contentScale = ContentScale.Fit
                    )
                    Text(text = publishername,modifier = modifier.padding(start = 16.dp, end = 16.dp)
                        , color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }


}
