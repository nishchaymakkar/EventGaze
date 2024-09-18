package com.minorproject.e_commerce.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.minorproject.e_commerce.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier : Modifier = Modifier) {
 OutlinedTextField(
    singleLine = true,
    modifier = modifier,
    value = value,
    onValueChange = { onNewValue(it) },
    label = { Text(stringResource(R.string.email)) },
    //leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
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
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedTrailingIconColor = MaterialTheme.colorScheme.primary
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
  PasswordField(value, R.string.repeat_password, onNewValue, modifier)
}

@ExperimentalMaterial3Api
@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
  PasswordField(value, R.string.password, onNewValue, modifier)
}
@ExperimentalMaterial3Api
@Composable
private fun PasswordField(
  value: String,
  @StringRes placeholder: Int,
  onNewValue: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
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

    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
    visualTransformation = visualTransformation,
    shape = MaterialTheme.shapes.large,
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedTrailingIconColor = MaterialTheme.colorScheme.primary
    )
  )
}