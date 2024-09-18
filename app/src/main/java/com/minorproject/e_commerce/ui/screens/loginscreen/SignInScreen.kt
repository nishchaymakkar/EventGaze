package com.minorproject.e_commerce.ui.screens.loginscreen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.minorproject.e_commerce.R
import com.minorproject.e_commerce.ui.common.components.BasicButton
import com.minorproject.e_commerce.ui.common.components.EmailTextField
import com.minorproject.e_commerce.ui.common.components.OrDivider
import com.minorproject.e_commerce.ui.common.components.PasswordField
import com.minorproject.e_commerce.ui.theme.ECommerceTheme

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
            modifier = modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(modifier = modifier
                .size(400.dp)
                .clip(shape = RectangleShape),
                contentScale = ContentScale.Fit, painter = painterResource(id = R.drawable.signinscreenimg), contentDescription = null)
        }
        Column(
            modifier.weight(1.5f)
        ) {


        Row(modifier = modifier
            .padding(start = 36.dp)
            .size(width = 100.dp, height = 50.dp)){
            Text(text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
                )
        }
        Column(
            modifier = modifier
                .padding(start = 36.dp, end = 36.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            EmailTextField(value = uiState.email, onNewValue = onEmailChange, modifier = modifier.fillMaxWidth())

            PasswordField(value = uiState.password, onNewValue = onPasswordChange, modifier = modifier.fillMaxWidth())
        }
        Row(
            modifier = modifier
                .padding(start = 36.dp, top = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.forgotPassword), style = MaterialTheme.typography.titleMedium, modifier = modifier.clickable(onClick = onForgotPasswordClick))
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
                .padding(start = 36.dp, end = 36.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.donthaveanacc))
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

@ExperimentalMaterial3Api
@Preview
@Composable
private fun SignInScreenPreview() {
    val uiState = SignInState(
       // email = "email@test.com"
    )
    ECommerceTheme {
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