package com.minorproject.eventgaze.ui.screens.signupscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.minorproject.eventgaze.ui.common.components.PasswordField
import com.minorproject.eventgaze.ui.common.components.RepeatPasswordField
import com.minorproject.eventgaze.ui.theme.EventGazeTheme

@ExperimentalMaterial3Api
@Composable
fun SignUpScreen(
   navigate: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(navigate) },
        onSignInClick = {viewModel.onSignInClick(popUp)}
    )
}
@ExperimentalMaterial3Api
@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Center

    ) {
        Spacer(modifier = modifier.height(40.dp))
        Row(
            modifier = modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(modifier = modifier
                .size(350.dp).padding(top = 20.dp, start = 8.dp, end = 8.dp)
                .clip(shape = RectangleShape),
                contentScale = ContentScale.Fit, painter = painterResource(id = R.drawable.signupscreenimage), contentDescription = null)
        }
        Column(
            modifier.weight(1.5f)
        ) {


            Row(modifier = modifier
                .padding(start = 16.dp)
                .size(width = 150.dp, height = 50.dp)){
                Text(text = stringResource(id = R.string.signup),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                EmailTextField(uiState.email, onEmailChange,modifier.fillMaxWidth())
                PasswordField(uiState.password, onPasswordChange,modifier.fillMaxWidth())
                RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, modifier.fillMaxWidth())}



            BasicButton(text = R.string.signup, modifier = modifier
                .fillMaxWidth()
                .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
                onSignUpClick,
            )


            Row(
                modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.alreadyhaveacc),
                    color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = stringResource(id = R.string.sign_in),
                    modifier= modifier.clickable(onClick = onSignInClick),
                    color =MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold)
            }
        }
    }

}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    EventGazeTheme  {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { },
            onSignInClick = {}
        )
    }
}