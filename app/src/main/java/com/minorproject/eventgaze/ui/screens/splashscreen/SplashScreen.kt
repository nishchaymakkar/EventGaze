package com.minorproject.eventgaze.ui.screens.splashscreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import kotlinx.coroutines.delay
const val SPlASH_TIMEOUT = 1000L
@Composable
fun SplashScreen(
    openAndPopUp: (String,String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
    ) {
    SplashScreenContent(
        onAppStart = {viewModel.onAppStart(openAndPopUp)}
    )

}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier,onAppStart: ()-> Unit){
    Column(modifier = modifier
        .background(color = MaterialTheme.colorScheme.onPrimary)
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Spacer(modifier.height(40.dp).fillMaxWidth())
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Image(painter = painterResource(id = R.drawable.musicimage), contentDescription = null, 
            modifier = modifier.size(width = 200.dp, height = 204.73.dp) )
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Image(painter = painterResource(id = R.drawable.codder), contentDescription = null,
                modifier = modifier.size(width = 200.dp, height = 109.94.dp) )
        }
        Row(modifier = modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp)/*.animateContentSize(animationSpec = spring(
            dampingRatio = 100f, stiffness = Spring.StiffnessMedium, visibilityThreshold = null)),*/,
            horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource( R.string.app_name), style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.secondary,
                modifier = modifier)
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Image(painter = painterResource(id = R.drawable.musiclistener), contentDescription = null,
                modifier = modifier.size(width = 200.dp, height = 152.54.dp) )
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Image(painter = painterResource(id = R.drawable.athlete), contentDescription = null,modifier = modifier.size(width = 300.dp, height = 194.69.dp) )
        }

    }

    LaunchedEffect(true) {
        delay(SPlASH_TIMEOUT)
        onAppStart()
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    EventGazeTheme {
        SplashScreenContent(
            onAppStart = {}
        )
    }

}