package com.minorproject.e_commerce.ui.screens.homescreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minorproject.e_commerce.R
import com.minorproject.e_commerce.ui.common.components.SearchTextField
import com.minorproject.e_commerce.ui.theme.ECommerceTheme





@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
private fun HomeScreenPreview() {
    ECommerceTheme {
        HomeScreenContent(query = "shoes", onQueryChange = {}, modifier = Modifier

        )
    }
}

@Composable
fun HomeScreenContent(query: String, onQueryChange: () -> Unit, modifier: Modifier ) {
    Column(modifier.fillMaxSize()) {
        Spacer(modifier = modifier.padding(20.dp))
    Row(modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Row(modifier.weight(3f)) {
            SearchTextField(query = query, onQueryChange = {})
        }
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = null,
            modifier = modifier.padding(start = 8.dp, end = 8.dp)
                .size(50.dp).weight(.5f)
        )
    }
    }
}
