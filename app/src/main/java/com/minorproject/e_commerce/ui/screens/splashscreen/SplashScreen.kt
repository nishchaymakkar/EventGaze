package com.minorproject.e_commerce.ui.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.rotationMatrix
import com.minorproject.e_commerce.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.e_commerce.ui.theme.ECommerceTheme
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
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(modifier = modifier.weight(1f)) {
        Image(painter = painterResource(id = R.drawable.groceries), contentDescription = null, modifier = modifier )
        }
        Row(modifier = modifier.weight(1f)) {
            Image(painter = painterResource(id = R.drawable.shoppingcart), contentDescription = null )
        }
        Row (modifier = modifier.weight(1f)){
            Image(painter = painterResource(id = R.drawable.windowshoping), contentDescription = null )
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
    ECommerceTheme {
        SplashScreenContent(
            onAppStart = {}
        )
    }

}