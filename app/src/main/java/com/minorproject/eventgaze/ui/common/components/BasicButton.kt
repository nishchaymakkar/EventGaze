package com.minorproject.eventgaze.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
  Button(
    onClick = action,
    modifier = modifier,
    colors =
      ButtonDefaults.buttonColors(
       containerColor =  MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
      ),
    shape = MaterialTheme.shapes.large,
    contentPadding = PaddingValues(15.dp)
  ) {
    Text(text = stringResource(text), textAlign = TextAlign.Center, maxLines = 1 , color = MaterialTheme.colorScheme.secondary)
  }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
 Button(
    onClick = action,
    colors =
    ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    )
  ) {
    Text(text = stringResource(text), color = MaterialTheme.colorScheme.onPrimary)
  }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors =ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.onPrimary,
      contentColor = MaterialTheme.colorScheme.primary
    ),
      border = BorderStroke(
          color = MaterialTheme.colorScheme.primary, width = 1.dp
      )
  ) {
    Text(text = stringResource(text))
  }
}
