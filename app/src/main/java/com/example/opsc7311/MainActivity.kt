package com.example.opsc7311

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSheetApp(modifier: Modifier = Modifier) {


    val localDateToDate = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )

    val timeToDate = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    val timeToDateDisplay = SimpleDateFormat(
        "hh:mm aa",
        Locale.getDefault()
    )

    val dateToTextDisplay = SimpleDateFormat(
        "dd MMMM yyyy",
        Locale.getDefault()
    )

    var selectedDate: Date by remember { mutableStateOf(Date()) }
    val formattedDate = dateToTextDisplay.format(selectedDate)

    var startTime: Date by remember { mutableStateOf(Date()) }
    val formattedStartTime = timeToDateDisplay.format(startTime)

    var endTime: Date by remember { mutableStateOf(Date()) }
    val formattedEndTime = timeToDateDisplay.format(endTime)


    val calendarState = UseCaseState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date { date ->
            selectedDate = localDateToDate.parse(date.toString()) as Date
        }
    )

    val clockState1 = UseCaseState()

    ClockDialog(
        state = clockState1,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            startTime = timeToDate.parse("${hours}:${minutes}") as Date

        }
    )

    val clockState2 = UseCaseState()

    ClockDialog(
        state = clockState2,
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            endTime = timeToDate.parse("${hours}:${minutes}") as Date
        }
    )

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        DateSurface(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            date = formattedDate,
            onIconClicked = {
                calendarState.show()
            }
        )
        TimeSurface(
            title = "Start Time",
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            time = formattedStartTime,
            onIconClicked = {
                clockState1.show()
            }
        )
        TimeSurface(
            title = "End Time",
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            time = formattedEndTime,
            onIconClicked = {
                clockState2.show()
            }
        )
    }

}

@Composable
fun DateSurface(
    date: String,
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
                    text = date,
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
fun TimeSurface(
    title: String,
    time: String,
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
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = time,
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


@Preview(showBackground = true)
@Composable
fun TimeSheetAppPreview() {
    Opsc7311Theme {
        TimeSheetApp(

        )
    }
}