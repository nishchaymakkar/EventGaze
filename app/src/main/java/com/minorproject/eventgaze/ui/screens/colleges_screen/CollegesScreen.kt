package com.minorproject.eventgaze.ui.screens.colleges_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.R


@ExperimentalMaterial3Api
@Composable
fun CollegesScreen(
    modifier: Modifier = Modifier,

){
    Column(modifier.fillMaxSize()) {
        Spacer(modifier = modifier.padding(20.dp))
        Row(modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.colleges), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary)
        }
        Column(modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "No Colleges Yet")
        }
    }


}
