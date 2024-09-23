package com.minorproject.eventgaze.ui.screens.colleges_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.minorproject.eventgaze.R
import com.minorproject.eventgaze.model.data.College
import com.minorproject.eventgaze.model.data.colleges

@ExperimentalMaterial3Api
@Composable
fun CollegesScreen(
    modifier: Modifier = Modifier,
    onCollegeClick: (College,String) -> Unit
){
    Column(modifier.fillMaxSize()) {
        Spacer(modifier = modifier.padding(20.dp))
        Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.colleges), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.secondary)
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            
        ) {
            itemsIndexed(colleges){ index,college ->
                val collegeNo = "College No.$index"
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable { onCollegeClick(college, collegeNo) },
                    border = BorderStroke(color = MaterialTheme.colorScheme.secondary, width = 1.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Row(
                        modifier
                            .fillMaxWidth()
                            .height(56.dp), verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Card(
                            shape = CircleShape,modifier = modifier.padding(start = 8.dp).size(40.dp)
                        ){
                            Image(
                                painter = painterResource(college.collegeImg), contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                        }
                        Spacer(modifier.width(10.dp))
                        Text(
                            text = college.collegeName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }


}
