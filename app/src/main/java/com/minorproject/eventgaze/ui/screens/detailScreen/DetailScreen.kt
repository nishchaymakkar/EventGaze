package com.minorproject.eventgaze.ui.screens.detailScreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.events


@ExperimentalSharedTransitionApi
@Composable
fun DetailScreen(
  eventId:  Int?,
  modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colorScheme.onPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        val event = events.find { it.id == eventId }
        if (event != null) {
            Image(
                painter = painterResource(event.image),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 40.dp)
                .aspectRatio(16 / 9f)
                .align(Alignment.Start)/*.sharedElement(
                    state = rememberSharedContentState(key= event.id ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initial, target ->
                        tween(durationMillis = 1000)
                    }
                )*/
            )
            Row(
                modifier = modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = event.title, style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text( text = event.des, style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Justify
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(8.dp).fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(event.profileimg),
                    contentDescription = null,
                    modifier = modifier,
                    contentScale = ContentScale.Fit
                )
                Text(text = event.publishername,
                    modifier = modifier.padding(start = 8.dp, end = 8.dp,), fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary)
            }

        }else  {
            Text(text = "event not found")
        }
    }
}