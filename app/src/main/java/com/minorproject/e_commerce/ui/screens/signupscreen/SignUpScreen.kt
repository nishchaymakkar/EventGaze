package com.minorproject.e_commerce.ui.screens.signupscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.minorproject.e_commerce.ui.common.components.EmailField
import com.minorproject.e_commerce.ui.common.components.EmailTextField
import com.minorproject.e_commerce.ui.common.components.OrDivider
import com.minorproject.e_commerce.ui.common.components.PasswordField
import com.minorproject.e_commerce.ui.common.components.RepeatPasswordField
import com.minorproject.e_commerce.ui.theme.ECommerceTheme

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
                contentScale = ContentScale.Fit, painter = painterResource(id = R.drawable.undraw_empty_cart_co35), contentDescription = null)
        }
        Column(
            modifier.weight(1f)
        ) {


            Row(modifier = modifier
                .padding(start = 36.dp)
                .size(width = 150.dp, height = 50.dp)){
                Text(text = stringResource(id = R.string.signup),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = modifier
                    .padding(start = 36.dp, end = 36.dp),
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
                    .padding(start = 36.dp, end = 36.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.alreadyhaveacc))
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

    ECommerceTheme {
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