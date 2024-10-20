package com.minorproject.eventgaze.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.minorproject.eventgaze.R

@Composable
fun OrgNameTextField(value: String, onNewValue: (String) -> Unit, modifier : Modifier = Modifier) {
  OutlinedTextField(value = value, onValueChange = { onNewValue(it) },
    modifier=modifier,
    label = { Text(text =stringResource(id = R.string.orgname), )},
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyLarge,
    shape = MaterialTheme.shapes.large,
    trailingIcon = { Icon(imageVector = Icons.Default.Public,contentDescription = null) },
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

@ExperimentalMaterial3Api
@Composable
fun EmailTextField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier){
  OutlinedTextField(value = value, onValueChange = { onNewValue(it) },
    modifier=modifier,
    label = { Text(text =stringResource(id = R.string.email), )},
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyLarge,
    shape = MaterialTheme.shapes.large,
    trailingIcon = { Icon(imageVector = Icons.Default.Email,contentDescription = null) },
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
@ExperimentalMaterial3Api
@Composable
fun RepeatPasswordField(
  value: String,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  PasswordField(value, R.string.repeat_password, onNewValue, modifier,ImeAction.Done)
}

@ExperimentalMaterial3Api
@Composable
fun PasswordFieldForSignIn(value: String, onNewValue: (String) -> Unit, modifier: Modifier= Modifier){
  PasswordField(value,R.string.password,onNewValue,modifier, imeAction = ImeAction.Done)
}

@ExperimentalMaterial3Api
@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
  PasswordField(value, R.string.password, onNewValue, modifier, imeAction = ImeAction.Next)
}
@ExperimentalMaterial3Api
@Composable
private fun PasswordField(
  value: String,
  @StringRes placeholder: Int,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
  imeAction: ImeAction
) {
  val focusManager  = LocalFocusManager.current
  var isVisible by remember { mutableStateOf(false) }

  val icon =
    if (isVisible) painterResource(R.drawable.ic_visibility_on)
    else painterResource(R.drawable.ic_visibility_off)

  val visualTransformation =
    if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

  OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    label = { Text(text = stringResource(placeholder)) },
    placeholder = {Text(text = stringResource(placeholder))},
    //leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
    trailingIcon = {
      IconButton(onClick = { isVisible = !isVisible }) {
        Icon(painter = icon, contentDescription = "Visibility")
      }
    },

    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),

    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = imeAction),
    visualTransformation = visualTransformation,
    shape = MaterialTheme.shapes.large,
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