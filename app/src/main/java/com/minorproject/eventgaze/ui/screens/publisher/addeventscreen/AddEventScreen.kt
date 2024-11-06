@file:OptIn(ExperimentalPermissionsApi::class)

package com.minorproject.eventgaze.ui.screens.publisher.addeventscreen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

import com.minorproject.eventgaze.ui.theme.EventGazeTheme
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Preview
@Composable
private fun AddEventScreenPreview() {

    EventGazeTheme {
        AddEventScreen(popUp = {}, retry = {})
    }
}
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun AddEventScreen(
    modifier: Modifier = Modifier,
    popUp: ()-> Unit,
    retry:()-> Unit,
    viewModel: AddEventViewModel = hiltViewModel()
) {
    val addEventUiState = viewModel.uiState
    val categoryOptions = listOf("Technologies","Sports","Dance","Music","Cooking","Arts")
    val scopeOptions = listOf("Local","National","Global")


    val context = LocalContext.current
    val publishEventResult by viewModel.publishEventResult.observeAsState()

    LaunchedEffect(publishEventResult) {
      publishEventResult?.let  {
            if (it.isSuccess) {
                Toast.makeText(context, "Event Published Successfully", Toast.LENGTH_SHORT).show()
                // Call the function to navigate back to the previous screen
                viewModel.popUp(popUp)
            } else {
                Toast.makeText(context, "Failed to Publish Event", Toast.LENGTH_SHORT).show()
            }
        }
    }


    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Spacer(
            modifier
                .height(20.dp)
                .fillMaxWidth())
        Row(
            modifier = modifier.padding(start = 16.dp, top = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.Cancel, contentDescription = null,
                modifier
                    .size(40.dp)
                    .clickable(onClick = { viewModel.popUp(popUp) })
            )
        }
        ImagePickerWithPermissions()
        EventNameTextField(
            value = addEventUiState.value.eventName,
            onNewValue = viewModel::onEventNameChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        DropdownTextField(
            label = "Select Event Category",
            options = categoryOptions,
            onValueSelected = { viewModel.onEventCategoryChange(it) }
        )
        DropdownTextField(
            label = "Select Event Scope",
            options = scopeOptions,
            onValueSelected = {viewModel.onEventScopeChange(it)}
        )
        EventDescriptionTextField(
            value = addEventUiState.value.eventDescription,
            onNewValue = viewModel::onEventDescriptionChange,
            modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        PublishButton(
            text = "Publish",
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            action = {viewModel.publishEvent(
                context = context,
                onFailure = {},
                onSuccess = { retry()},
            )}
        )

    }

}
@ExperimentalMaterial3Api
@Composable
fun DropdownTextField(
    label: String,
    options: List<String>,
    onValueSelected: (String) -> Unit,
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
                .padding(start = 16.dp, end = 16.dp)
                .menuAnchor() // Ensures alignment with the dropdown menu
                .fillMaxWidth()
                .clickable { expanded = !expanded }, // Click on the field to show the dropdown
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = if (imeAction){
                ImeAction.Done
            } else {
                ImeAction.None
            }
            ),
            keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.background,
                focusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,

            )
        )

        // Dropdown menu with items
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.tertiary,
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    colors = MenuItemColors(textColor = MaterialTheme.colorScheme.secondary,
                        leadingIconColor = MaterialTheme.colorScheme.secondary,
                        trailingIconColor = MaterialTheme.colorScheme.secondary,
                        disabledTextColor = MaterialTheme.colorScheme.secondary,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.secondary,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.secondary),
                    onClick = {
                        imeAction = true
                        selectedOption = option
                        expanded = false
                        onValueSelected(option) // Pass the selected option to the parent composable
                    },modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                )
            }
        }
    }
}





@Composable
fun EventNameTextField(value: String, onNewValue: (String) -> Unit, modifier : Modifier = Modifier) {


    OutlinedTextField(value = value, onValueChange = { onNewValue(it) },
        modifier=modifier,
        label = { Text(text = "Event Name" ) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.large,
        trailingIcon = { Icon(imageVector = Icons.Default.Event,contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            focusedLabelColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Composable
fun EventDescriptionTextField(value: String, onNewValue: (String) -> Unit, modifier : Modifier = Modifier) {
val focusManager = LocalFocusManager.current

    OutlinedTextField(value = value, onValueChange = { onNewValue(it) },
        modifier=modifier,
        label = { Text(text = "Event Description" ) },
        minLines = 5,
        maxLines = 5,
        textStyle = MaterialTheme.typography.bodyLarge,
        shape = MaterialTheme.shapes.large,
        placeholder = { Icon(imageVector = Icons.Default.Description,contentDescription = null) },
        keyboardActions = KeyboardActions(onDone ={ focusManager.clearFocus()}),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            focusedLabelColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@Composable
fun PublishButton(text: String, modifier: Modifier, action: () -> Unit) {
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
        Text(text = text, textAlign = TextAlign.Center, maxLines = 1 )
    }
}

@ExperimentalPermissionsApi
@Composable
fun ImagePickerWithPermissions() {
    // Permission state for reading external storage
    val storagePermissionState = rememberPermissionState(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    // Check permission status
    when {
        storagePermissionState.status.isGranted -> {
            // If permission is granted, show the image picker card
            ImagePickerCard()
        }
        storagePermissionState.status.shouldShowRationale -> {
            // Show rationale if permission is not granted and a rationale should be displayed
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Storage permission is needed to select an image.")
                Button(onClick = { storagePermissionState.launchPermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }
        else -> {
            // Request permission if it hasn't been granted or denied
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Permission is required to select an image.")
                Button(onClick = { storagePermissionState.launchPermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }
    }
}


@Composable
fun ImagePickerCard(viewModel: AddEventViewModel= hiltViewModel()) {
    // State for selected image URI
    val selectedImageUri by viewModel.selectedImageUri

    // Create a launcher to open the image picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> viewModel.onImageSelected(uri) }
    )

    // Card to select or edit the image
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable { launcher.launch("image/*") }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (selectedImageUri != null) {
                // Display selected image
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Edit button in the top-right corner
                IconButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Gray.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Image",
                        tint = Color.White
                    )
                }
            } else {
                // Display placeholder content for the empty card
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Add Image",
                        modifier = Modifier.size(48.dp),
                        tint = Color.Gray
                    )
                    Text("Tap to select an image")
                }
            }
        }
    }
}