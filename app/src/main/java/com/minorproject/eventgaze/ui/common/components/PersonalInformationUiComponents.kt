package com.minorproject.eventgaze.ui.common.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.minorproject.eventgaze.R
import kotlinx.coroutines.flow.StateFlow


@Composable
fun NameTextField(value: String, onNewValue: (String) -> Unit, modifier : Modifier = Modifier) {


    OutlinedTextField(value = value, onValueChange = { onNewValue(it) },
        modifier=modifier,
        label = { Text(text = stringResource(id = R.string.userName), ) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.large,
        trailingIcon = { Icon(imageVector = Icons.Default.Person,contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary
        )
    )
}
@Composable
fun CollegeNameTextField(value: String, onNewValue: (String) -> Unit, modifier : Modifier = Modifier) {
    val focusManager  = LocalFocusManager.current
    OutlinedTextField(value = value, onValueChange = { onNewValue(it) },
        modifier=modifier,
        label = { Text(text =stringResource(id = R.string.collegeName), )},
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.large,
        trailingIcon = { Icon(imageVector = Icons.Default.Public,contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary
        )
    )
}
