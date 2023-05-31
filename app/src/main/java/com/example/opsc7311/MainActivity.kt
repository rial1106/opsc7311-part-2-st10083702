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
import java.time.LocalDate
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


@Composable
fun TimeSheetApp() {


    /* The dates and time are in a Date() class so we need to convert
     * them to Strings.
     */
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

    /* The values outputted by the pickers are stored here and their string
     * counterparts
     */
    var selectedDate: Date by remember { mutableStateOf(Date()) }
    val formattedDateString = dateToTextDisplay.format(selectedDate)

    var startTime: Date by remember { mutableStateOf(Date()) }
    val formattedStartTimeString = timeToDateDisplay.format(startTime)

    var endTime: Date by remember { mutableStateOf(Date()) }
    val formattedEndTimeString = timeToDateDisplay.format(endTime)


    // Calendar picker
    val calendarState = UseCaseState()
    DateCalPicker(calendarState = calendarState,
        onSelectDate = { date ->
            selectedDate = localDateToDate.parse(date.toString()) as Date
        })

    // First time picker
    val clockState1 = UseCaseState()
    TimeClockPicker(clockState = clockState1, onTimeSelected = { hours, minutes ->
        startTime = timeToDate.parse("${hours}:${minutes}") as Date
    })

    // Second time picker
    val clockState2 = UseCaseState()
    TimeClockPicker(clockState = clockState2, onTimeSelected = { hours, minutes ->
        endTime = timeToDate.parse("${hours}:${minutes}") as Date
    })

    // Show the column of pickers and dates
    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        DateSurface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            date = formattedDateString,
            onIconClicked = {
                calendarState.show()
            }
        )
        TimeSurface(
            title = "Start Time",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            time = formattedStartTimeString,
            onIconClicked = {
                clockState1.show()
            }
        )
        TimeSurface(
            title = "End Time",
            modifier = Modifier
                .fillMaxWidth(),
            time = formattedEndTimeString,
            onIconClicked = {
                clockState2.show()
            }
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalPicker(
    calendarState: UseCaseState,
    onSelectDate: (LocalDate) -> Unit
)
{
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date (onSelectDate = onSelectDate)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeClockPicker(
    clockState: UseCaseState,
    onTimeSelected: (Int, Int) -> Unit

) {
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes(onPositiveClick = onTimeSelected)
    )
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