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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.modal.data.College
import com.minorproject.eventgaze.modal.data.EventCategory
import com.minorproject.eventgaze.ui.common.ComplexGradientBackground
import com.minorproject.eventgaze.ui.common.components.BasicButton
import com.minorproject.eventgaze.ui.common.components.EmailTextField
import com.minorproject.eventgaze.ui.common.components.NameTextField
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
    val collegeOptions by viewModel.collegeOptions.collectAsState()
    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(navigate) },
        onSignInClick = {viewModel.onSignInClick(popUp)},
        onOrgNameChange = viewModel::onPublisherOrgNameChange,
        onFirstNameChange = viewModel::onFirstNameChange,
        collegeOptions = collegeOptions,
        onCollegeChange = viewModel::onCollegeChange,
        viewModel = viewModel

    )
}
@ExperimentalMaterial3Api
@Composable
fun SignUpScreenContent(
    onOrgNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    onCollegeChange: (College) -> Unit,
    collegeOptions: List<College> = emptyList(),
    viewModel: SignUpViewModel
) {

    var selectedTab = viewModel.selectedTabIndex
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
                SignUpTab(selectedTab = selectedTab, onTabSelected = { viewModel.onSelectedTabChange(it) })

            }
            if (selectedTab == 0) {
                Column(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NameTextField(uiState.firstName, onFirstNameChange,modifier.fillMaxWidth())
                    DropdownCollegeTextField(label = "Select College",
                        options = collegeOptions, onValueSelected = onCollegeChange)
                    EmailTextField(uiState.userEmail, onEmailChange, modifier.fillMaxWidth())
                    PasswordField(uiState.userPassword, onPasswordChange, modifier.fillMaxWidth())
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
                    OrgNameTextField(uiState.publisherOrgName, onOrgNameChange, modifier.fillMaxWidth())
                    EmailTextField(uiState.userEmail, onEmailChange, modifier.fillMaxWidth())
                    PasswordField(uiState.userPassword, onPasswordChange, modifier.fillMaxWidth())
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
        userEmail =  "email@test.com",

    )

    EventGazeTheme  {
        SignUpScreenContent(
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { },
            onSignInClick = {},
            onOrgNameChange = {},
            onFirstNameChange = {},
            onCollegeChange = {},
            viewModel = hiltViewModel()
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

@ExperimentalMaterial3Api
@Composable
fun DropdownCollegeTextField(
    label: String,
    options: List<College>,
    onValueSelected: (College) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var imeAction by remember { mutableStateOf(false) }
    // Outer Box to handle the ExposedDropdownMenu logic
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField to display selected value or hint
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { selectedOption = it },
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .menuAnchor() // Ensures alignment with the dropdown menu
                .fillMaxWidth()
                .clickable { expanded = !expanded }, // Click on the field to show the dropdown
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            },
            
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary
                )
        )

        // Dropdown menu with items
        ExposedDropdownMenu(
            shape = RoundedCornerShape(16.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.collegeName) },
                    colors = MenuItemColors(textColor = MaterialTheme.colorScheme.secondary,
                        leadingIconColor = MaterialTheme.colorScheme.secondary,
                        trailingIconColor = MaterialTheme.colorScheme.secondary,
                        disabledTextColor = MaterialTheme.colorScheme.secondary,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.secondary,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.secondary),
                    onClick = {
                        imeAction = true
                        selectedOption = option.collegeName
                        expanded = false
                        onValueSelected(College(collegeName = option.collegeName, collegeId = option.collegeId, collegeAddress = option.collegeAddress, collegeImage = option.collegeImage)) // Pass the selected option to the parent composable
                    },modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                )
            }
        }
    }
}