package com.minorproject.eventgaze.ui.screens.loginscreen

import android.graphics.RenderEffect.*
import android.graphics.Shader
import android.graphics.Shader.TileMode
import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.ui.common.components.BasicButton
import com.minorproject.eventgaze.ui.common.components.EmailTextField
import com.minorproject.eventgaze.ui.common.components.OrDivider
import com.minorproject.eventgaze.ui.common.components.PasswordField
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.graphics.RenderEffect
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Card
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.ShaderBrush
import com.minorproject.eventgaze.ui.theme.primary

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterial3Api
@Composable
fun SignInScreen(
    openAndPopUp: (String, String) -> Unit,
    navigate: (String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignInScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange ,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp)},
        onSignUpClick = { viewModel.onSignUpClick(navigate) },
        onForgotPasswordClick = viewModel::onForgotPasswordClick)



}

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterial3Api
@Composable
fun SignInScreenContent(modifier: Modifier = Modifier,
    uiState: SignInState,
    onEmailChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: ()  -> Unit,
    onForgotPasswordClick: () -> Unit,
    onPasswordChange: (String)-> Unit,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary)
//            .verticalScroll(
//                rememberScrollState()
//            ),
    ) {
        Row(
            modifier = modifier.weight(1f) ,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(modifier = modifier
                .size(400.dp)
                .clip(shape = RectangleShape).padding(start = 8.dp, end = 8.dp),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = R.drawable.signinscreenimage),
                contentDescription = null)
        }
        Column(
            modifier.weight(1.5f)
        ) {


        Row(modifier = modifier
            .padding(start = 16.dp)
            .size(width = 100.dp, height = 50.dp)){
            Text(text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
                )
        }
        Column(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            EmailTextField(value = uiState.email, onNewValue = onEmailChange, modifier = modifier.fillMaxWidth())

            PasswordField(value = uiState.password, onNewValue = onPasswordChange, modifier = modifier.fillMaxWidth())
        }
        Row(
            modifier = modifier
                .padding(start = 36.dp, top = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.forgotPassword),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier.clickable(onClick = onForgotPasswordClick))
        }

        BasicButton(text = R.string.login, modifier = modifier
            .fillMaxWidth()
            .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
          action =   onSignInClick,
        )
        OrDivider()

        BasicButton(text = R.string.login_google, modifier = modifier
            .fillMaxWidth()
            .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
            action = onSignInClick,
        )
        Row(
            modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.donthaveanacc),color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = modifier.size(4.dp))
            Text(text = stringResource(id = R.string.signup),
                modifier= modifier.clickable(onClick = onSignUpClick),
                color =MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)
        }
    }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterial3Api
@Preview
@Composable
private fun SignInScreenPreview() {
    val uiState = SignInState(
       // email = "email@test.com"
    )
    EventGazeTheme  {
        SignInScreenContent(
            uiState = uiState,
            onEmailChange = {},
            onSignInClick = {},
            onForgotPasswordClick = {},
            onPasswordChange = {},
            onSignUpClick = {}
        )
    }
    
}