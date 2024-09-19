@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.minorproject.eventgaze.ui.common.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppBar(
    onBackPressed: ()-> Unit = {},
    //screenName: String? ,
    modifier: Modifier
) {
    TopAppBar(title = {/*screenName */}, navigationIcon = {IconButton(onClick = { onBackPressed}){
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
        )
    }

    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
    ) )
}

@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(value = query, onValueChange = onQueryChange,
        placeholder = { Text(text = "search here")},
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        shape = MaterialTheme.shapes.large,
        trailingIcon =  { Icon(imageVector = Icons.Default.Search,contentDescription = null)},
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.LightGray
        )
    )
}
