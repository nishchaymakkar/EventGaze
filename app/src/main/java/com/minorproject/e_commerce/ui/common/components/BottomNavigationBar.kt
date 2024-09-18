package com.minorproject.e_commerce.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit ,
    onProfileClick: ()-> Unit ,
    onCartClick: ()-> Unit ,
){
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onClick = onHomeClick) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
        }
        IconButton(onClick = onCartClick) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
        }
        IconButton(onClick = onProfileClick) {
        Icon(imageVector = Icons.Default.Person, contentDescription = null)
        }
    }
}