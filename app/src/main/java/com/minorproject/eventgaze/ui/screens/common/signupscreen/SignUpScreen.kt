package com.minorproject.eventgaze.ui.screens.common.signupscreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.ui.common.ComplexGradientBackground
import com.minorproject.eventgaze.ui.common.components.BasicButton
import com.minorproject.eventgaze.ui.common.components.EmailTextField
import com.minorproject.eventgaze.ui.common.components.OrgNameTextField
import com.minorproject.eventgaze.ui.common.components.PasswordField
import com.minorproject.eventgaze.ui.common.components.RepeatPasswordField
import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

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
        onSignInClick = {viewModel.onSignInClick(popUp)},
        onOrgNameChange = viewModel::onOrgNameChange
    )
}
@ExperimentalMaterial3Api
@Composable
fun SignUpScreenContent(
    onOrgNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val hazeState = remember { HazeState() }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimary),
    ) {
        ComplexGradientBackground()
        Column(modifier.align(Alignment.Center).hazeChild(hazeState),verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp).fillMaxWidth()
                    .size(width = 150.dp, height = 50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.signup),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp).fillMaxWidth()
            ) {
                SignUpTab(selectedTab = selectedTab, onTabSelected = { newTab ->
                    selectedTab = newTab

                })

            }
            if (selectedTab == 0) {
                Column(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EmailTextField(uiState.email, onEmailChange, modifier.fillMaxWidth())
                    PasswordField(uiState.password, onPasswordChange, modifier.fillMaxWidth())
                    RepeatPasswordField(
                        uiState.repeatPassword,
                        onRepeatPasswordChange,
                        modifier.fillMaxWidth()
                    )
                }



                BasicButton(
                    text = R.string.signup,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
                    onSignUpClick,
                )


            } else {
                Column(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OrgNameTextField(uiState.orgName, onOrgNameChange, modifier.fillMaxWidth())
                    EmailTextField(uiState.email, onEmailChange, modifier.fillMaxWidth())
                    PasswordField(uiState.password, onPasswordChange, modifier.fillMaxWidth())
                    RepeatPasswordField(
                        uiState.repeatPassword,
                        onRepeatPasswordChange,
                        modifier.fillMaxWidth()
                    )
                }



                BasicButton(
                    text = R.string.signup,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, top = 20.dp, bottom = 20.dp),
                    onSignUpClick,
                )

            }
            Row(
                modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.alreadyhaveacc),
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = modifier.size(4.dp))
                Text(
                    text = stringResource(id = R.string.sign_in),
                    modifier = modifier.clickable(onClick = onSignInClick),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
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
            onSignInClick = {},
            onOrgNameChange = {}
        )
    }
}



@Composable
fun SignUpTab(selectedTab: Int, onTabSelected: (Int) -> Unit) {

    // Background color of the Tab Row
    Box(
        modifier = Modifier
            .fillMaxWidth()
             // Use your desired blue color
            .padding(top = 16.dp, bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier

                    .background(
                        color = MaterialTheme.colorScheme.primary, // Outer tab background color
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(1.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    // Active Tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (selectedTab == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable(onClick = {onTabSelected(0) })
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.user), fontWeight = FontWeight.Medium,
                            color = if (selectedTab == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    // Completed Tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (selectedTab == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onTabSelected(1)}
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.publisher), fontWeight =FontWeight.Medium,
                            color = if (selectedTab == 1)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }

}