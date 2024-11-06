package com.minorproject.eventgaze.ui.screens.user.profilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minorproject.eventgaze.ui.common.components.CollegeNameTextField
import com.minorproject.eventgaze.ui.common.components.EmailTextField
import com.minorproject.eventgaze.ui.common.components.NameTextField


@ExperimentalMaterial3Api
@Composable
fun PiScreen(modifier: Modifier = Modifier,
             popUp: ()-> Unit,
             viewModel: PiInformationViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.onPrimary).padding(start = 16.dp, end = 16.dp, top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val uiState by viewModel.uiState
        Row(modifier = modifier.fillMaxWidth()) {
            IconButton(onClick = {popUp()}) {
                Icon(imageVector = Icons.Default.ArrowBack,contentDescription = null)
            }
        }
        NameTextField(value =uiState.userName, onNewValue = viewModel::onUserNameChange,modifier.fillMaxWidth())
        EmailTextField(value = uiState.emailId, onNewValue = viewModel::onEmailChange,modifier.fillMaxWidth())
        CollegeNameTextField(value = uiState.collegeName, onNewValue = viewModel::onCollegeNameChange,modifier.fillMaxWidth() )
    }
}