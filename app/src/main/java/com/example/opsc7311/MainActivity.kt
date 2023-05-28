package com.example.opsc7311

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opsc7311.ui.theme.Opsc7311Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Opsc7311Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TimeSheetApp()
                }
            }
        }
    }
}


@Composable
fun TimeSheetApp(modifier: Modifier = Modifier) {

    var showDatePicker by remember { mutableStateOf(false) }
    if (showDatePicker) {
        // Show it
    }

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        DateSurface(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            onIconClicked = {
                showDatePicker = true
            }
        )
        TimeSurface(
            title = "Start Time",
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        TimeSurface(
            title = "End Time",
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

}

@Composable
fun DateSurface(
    onIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "11 May 2023",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            IconButton(onClick = onIconClicked) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}

@Composable
fun TimeSurface(title: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "7:43AM",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TimeSheetAppPreview() {
    Opsc7311Theme {
        TimeSheetApp(

        )
    }
}