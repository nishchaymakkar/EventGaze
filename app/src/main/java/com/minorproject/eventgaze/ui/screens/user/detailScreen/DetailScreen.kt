package com.minorproject.eventgaze.ui.screens.user.detailScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.Event


@ExperimentalSharedTransitionApi
@Composable
fun DetailScreen(
    event: List<Event>,
  eventId:  Int?,
  modifier: Modifier = Modifier,
  animatedVisibilityScope: AnimatedVisibilityScope
) {
    SharedTransitionLayout {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.onPrimary),
            horizontalAlignment = Alignment.CenterHorizontally,) {
            val event = event.find { it.id == eventId }
            if (event != null) {
                Image(
                    painter = painterResource(R.drawable.img_3),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .aspectRatio(16 / 9f)
                        .align(Alignment.Start).sharedElement(
                    state = rememberSharedContentState(key= event.id ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { initial, target ->
                        tween(durationMillis = 1000)
                    }
                )
                )
                Row(
                    modifier = modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp)
                ) {
                    Text(
                        text = event.title, style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary, modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key= event.title ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initial, target ->
                                tween(durationMillis = 1000)
                            }
                        )
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text( text = event.des, style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Justify, modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key= event.des),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initial, target ->
                                tween(durationMillis = 1000)
                            }
                    )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(8.dp).fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(R.drawable.img),
                        contentDescription = null,
                        modifier =  Modifier.sharedElement(
                            state = rememberSharedContentState(key= event ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { initial, target ->
                                tween(durationMillis = 1000)
                            }
                        ),
                        contentScale = ContentScale.Fit
                    )
                    Text(text = event.publishername,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp,)
//                            .sharedElement(
//                                state = rememberSharedContentState(key= event.id ),
//                        animatedVisibilityScope = animatedVisibilityScope,
//                        boundsTransform = { initial, target ->
//                            tween(durationMillis = 1000)
//                        }
//                    )
                        , fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary)
                }

            }else  {
                Text(text = "event not found")
            }
        }
    }

}