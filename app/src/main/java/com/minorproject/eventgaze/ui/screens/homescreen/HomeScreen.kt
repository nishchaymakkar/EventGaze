@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.minorproject.eventgaze.ui.screens.homescreen


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.minorproject.eventgaze.model.data.events
import com.minorproject.eventgaze.ui.theme.EventGazeTheme


@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun HomeScreenPreview() {
    EventGazeTheme {
        //HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent( modifier: Modifier = Modifier,onItemClick: (Event, Int) -> Unit) {

    Column(modifier.fillMaxSize()) {
        Spacer(modifier = modifier.padding(20.dp))
        Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.discover), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary)
        }

        var selectedCategoryId by remember { mutableStateOf(1) }
        CategoryRow(categories = categories, selectedCategoryId = selectedCategoryId, onCategorySelected = {selectedCategoryId = it})
        Spacer(modifier.height(20.dp))
        EventList(events = events, selectedCategoryId = selectedCategoryId,modifier = modifier, onItemClick = onItemClick  )
    }
}

@Composable
fun CategoryRow(
    categories: List<Category>,
    selectedCategoryId: Int,
    onCategorySelected: (Int) -> Unit
){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(start=16.dp,end= 16.dp)) {
        items(categories){category ->
            val isSelected = selectedCategoryId == category.id

        Column(
            modifier = Modifier.clickable(onClick = {onCategorySelected(category.id)}),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier.size(55.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary
                ), border = BorderStroke(width = 1.dp, color = if (isSelected)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
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

@Composable
fun EventList(events: List<Event>, selectedCategoryId: Int,modifier: Modifier,onItemClick: (Event,Int)-> Unit ){
    val filteredEvents = if (selectedCategoryId == 1 ) {
        events
    } else {
        events.filter { it.categoryid == selectedCategoryId }
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.padding(bottom= 80.dp)) {
        itemsIndexed(filteredEvents){index,event->

            ItemCard(
                image = event.image, title = event.title,
                des = event.des, modifier = modifier.clickable { onItemClick(event,index) },
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
            modifier.fillMaxSize().background(brush = gradientColor), verticalArrangement = Arrangement.SpaceBetween
        ){
        Image(painter = painterResource(image), contentDescription = null,modifier = modifier
            .fillMaxWidth().align(Alignment.Start)
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