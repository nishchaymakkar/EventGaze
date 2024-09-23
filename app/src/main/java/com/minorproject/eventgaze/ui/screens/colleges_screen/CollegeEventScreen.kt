package com.minorproject.eventgaze.ui.screens.colleges_screen

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.geometry.Offset
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.minorproject.eventgaze.DetailScreen
import com.minorproject.eventgaze.model.data.Event
import com.minorproject.eventgaze.model.data.colleges
import com.minorproject.eventgaze.model.data.events

@Preview
@Composable
private fun CollegeEventScreenPreview() {
    CollegeEventScreen(
        collegeId = 1,
        detailnavigate = {}
    )
}
@Composable
fun CollegeEventScreen(
    collegeId: Int?,
    modifier: Modifier = Modifier,
    detailnavigate: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(MaterialTheme.colorScheme.onPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        val college = colleges.find { it.collegeId == collegeId }
        if (college != null) {

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),

            ) {
                Image(
                    painter = painterResource(college.collegeImg),
                    contentDescription = null,
                    modifier = Modifier
                        .aspectRatio(16 / 9f), contentScale = ContentScale.Crop/*.sharedElement(
                    state = rememberSharedContentState(key= event.id ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initial, target ->
                        tween(durationMillis = 1000)
                    }
                )*/
                )

                Row(
                    modifier.align(Alignment.BottomStart).fillMaxWidth().background(brush = Brush.linearGradient(listOf(Color.White,Color.Transparent),
                        start = Offset(10f,10f), end = Offset(700f,700f)))
                ) {
                    Text(
                        text = college.collegeName, style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 3,
                        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom =  10.dp)
                    )
                }
            }
            EventList(
                events = events,
                modifier = modifier,
                detailnavigate = detailnavigate
            )


        }else  {
            Text(text = "college not found")
        }
    }

}


@Composable
fun EventList(events: List<Event>,modifier: Modifier, detailnavigate: (String) -> Unit, viewModel: CollegeEventViewModel = viewModel()){

    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.padding(bottom = 8.dp)) {
        itemsIndexed(events){index,event->

            ItemCard(
                image = event.image, title = event.title,
                des = event.des, modifier = modifier.clickable { viewModel.onItemClick(event.id,detailnavigate) },
                profileimg = event.profileimg,
                publishername = event.publishername,

                )

        }
    }
}


@Composable
private fun ItemCard(
    image: Int,
    title: String,
    des: String,
    modifier: Modifier,
    profileimg: Int,
    publishername: String,

    ) {
    var backgroundColor by remember { mutableStateOf(androidx.compose.ui.graphics.Color.Transparent) }
    val context = LocalContext.current

    LaunchedEffect(image) {
        // Load image as a Bitmap
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
                    backgroundColor = Color(it)
                }
            }
        }
    }

    val lightenedColor = backgroundColor.copy(alpha = 0.4f)
    val gradientColor = Brush.linearGradient(
        colors = listOf(backgroundColor, lightenedColor),
        start = Offset(0f, 0f),
        end = Offset(500f, 500f) // Adjust as necessary for the gradient effect
    )

    Card(modifier = modifier
        .height(360.dp)
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp), colors = CardDefaults.cardColors(
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    )) {
        Column (
            modifier
                .fillMaxSize()
                .background(brush = gradientColor), verticalArrangement = Arrangement.SpaceBetween
        ){
            Image(painter = painterResource(image), contentDescription = null,modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .height(200.dp)
                .padding(8.dp), contentScale = ContentScale.Crop )
            Row(

            ) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, color = androidx.compose.ui.graphics.Color.White,
                    modifier = modifier.padding(8.dp))
            }
            Row {
                Text(
                    text = des,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start, modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(profileimg),
                    contentDescription = null,
                    modifier = modifier,
                    contentScale = ContentScale.Fit
                )
                Text(text = publishername,modifier = modifier.padding(start = 8.dp, end = 8.dp), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}