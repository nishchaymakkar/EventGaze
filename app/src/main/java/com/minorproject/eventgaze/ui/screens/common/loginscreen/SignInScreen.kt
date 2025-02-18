package com.minorproject.eventgaze.ui.screens.common.loginscreen

import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaParser
import android.media.MediaPlayer
import android.net.Uri
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
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
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.minorproject.eventgaze.ui.common.ComplexGradientBackground
import com.minorproject.eventgaze.ui.common.components.PasswordFieldForSignIn
import com.minorproject.eventgaze.ui.common.components.SnackbarManager

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterial3Api
@Composable
fun SignInScreen(
    openAndPopUp: (String, String) ->Unit,
    navigate: (String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState
//    val activity = LocalContext.current as Activity
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//        try {
//            val account = task.getResult(ApiException::class.java)
//            viewModel.handleGoogleSignInResult(account, openAndPopUp)
//        } catch (e: ApiException) {
//            Log.d("Sign In Failed", "$e")
//            SnackbarManager.showMessage(R.string.sign_in_failed)
//        }
//    }
    val context = LocalContext.current
    val loginResult by viewModel.loginResult.observeAsState()

    LaunchedEffect(loginResult) {
      loginResult?.let {
            if (it.isSuccess) {
                Toast.makeText(context, "Successfully Log In", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Log In failed please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
    SignInScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange ,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp)},
        onSignUpClick = { viewModel.onSignUpClick(navigate)},
        //onForgotPasswordClick = viewModel::onForgotPasswordClick,
        //onLoginWithGoogle = {launcher.launch(viewModel.getGoogleSignInIntent(activity))})
    )


}

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalMaterial3Api
@Composable
fun SignInScreenContent(modifier: Modifier = Modifier,
                        uiState: SignInState,
                        onEmailChange: (String) -> Unit,
                        onSignInClick: () -> Unit,
                        onSignUpClick: ()  -> Unit,
                      //  onForgotPasswordClick: () -> Unit,
                        onPasswordChange: (String)-> Unit,
                     //   onLoginWithGoogle: () -> Unit
){

   Box(Modifier.fillMaxSize()) {
       ComplexGradientBackground()

        Column(
            modifier = modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf( Color.Transparent,
                        Color.Black)
                    )
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
//            .verticalScroll(
//                rememberScrollState()
//            ),
        ) {
            Row(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .size(width = 150.dp, height = 50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }


            Column(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EmailTextField(
                    value = uiState.email,
                    onNewValue = onEmailChange,
                    modifier = modifier.fillMaxWidth()
                )

                PasswordFieldForSignIn(
                    value = uiState.password,
                    onNewValue = onPasswordChange,
                    modifier = modifier.fillMaxWidth()
                )
            }
//            Row(
//                modifier = modifier
//                    .padding(start = 36.dp, top = 16.dp)
//            ) {
//                Text(
//                    text = stringResource(id = R.string.forgotPassword),
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.secondary,
//                    modifier = modifier.clickable(onClick = onForgotPasswordClick)
//                )
//            }

            BasicButton(
                text = R.string.login,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
                action = onSignInClick,
            )
           // OrDivider()

//            BasicButton(
//                text = R.string.login_google, modifier = modifier
//                    .fillMaxWidth()
//                    .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
//                action = onLoginWithGoogle
//            )
            Row(
                modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.donthaveanacc),
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = modifier.size(4.dp))
                Text(
                    text = stringResource(id = R.string.signup),
                    modifier = modifier.clickable(onClick = onSignUpClick),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
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
            //onForgotPasswordClick = {},
            onPasswordChange = {},
            onSignUpClick = {},
            //onLoginWithGoogle = {}
        )
    }
    
}




