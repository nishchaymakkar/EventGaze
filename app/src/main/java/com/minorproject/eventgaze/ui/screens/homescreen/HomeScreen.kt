package com.minorproject.eventgaze.ui.screens.homescreen


import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.ui.common.components.SearchTextField
import com.minorproject.eventgaze.ui.theme.EventGazeTheme


@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
private fun HomeScreenPreview() {
    EventGazeTheme {
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent( modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Spacer(modifier = modifier.padding(20.dp))
        Row(modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.discover), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
