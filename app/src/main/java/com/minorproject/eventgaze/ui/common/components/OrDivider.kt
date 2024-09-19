package com.minorproject.eventgaze.ui.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import  androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.minorproject.eventgaze.R

@Composable
fun OrDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 2.dp, start = 36.dp, end = 36.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color =Color.Gray
        )
        Text(
            text = stringResource(id = R.string.or),
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Gray
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp),
            color = Color.Gray
        )
    }
}
