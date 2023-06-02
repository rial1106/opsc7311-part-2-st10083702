package com.example.opsc7311

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

    val timeToDateDisplay = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    val dateToTextDisplay = SimpleDateFormat(
        "dd MMMM yyyy",
        Locale.getDefault()
    )

    /* The values outputted by the pickers are stored here and their string
     * counterparts
     */
    var selectedDate by remember { mutableStateOf(Date()) }
    val formattedDateString = dateToTextDisplay.format(selectedDate)

    var startTime by remember { mutableStateOf(timeToDateDisplay.format(Date())) }

    var endTime by remember { mutableStateOf(timeToDateDisplay.format(Date())) }

    var showImageDetail by remember { mutableStateOf(false) }
    var selectedImageId by remember { mutableStateOf(R.drawable.image_1) }

    // Calendar picker
    val calendarState = UseCaseState()
    DateCalPicker(calendarState = calendarState,
        onSelectDate = { date ->
            selectedDate = localDateToDate.parse(date.toString()) as Date
        })

    // First time picker
    val clockState1 = UseCaseState()
    TimeClockPicker(clockState = clockState1, onTimeSelected = { hours, minutes ->
        startTime = "${hours}:${minutes}"
    })

    // Second time picker
    val clockState2 = UseCaseState()
    TimeClockPicker(clockState = clockState2, onTimeSelected = { hours, minutes ->
        endTime = "${hours}:${minutes}"
    })

    // Show the column of pickers and dates
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
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
            time = startTime,
            onIconClicked = {
                clockState1.show()
            }
        )
        TimeSurface(
            title = "End Time",
            modifier = Modifier
                .fillMaxWidth(),
            time = endTime,
            onIconClicked = {
                clockState2.show()
            }
        )

        DurationDisplay(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 28.dp)
                .height(120.dp)
                .width(160.dp),
            startTime = startTime,
            endTime = endTime
        )

        CategorySelector(
            modifier = Modifier,
            onCategoryClick = {},
            categories = listOf(
                "test", "butter", "mash",
                "apples", "cream", "tomato", "lemon",
                "sauce", "night-cream", "zebra", "lion", "coffee"
            )
        )

        ImageSelector(
            modifier = Modifier.height(160.dp),
            onManageImagesClick = {},
            images = listOf(
                R.drawable.image_1, R.drawable.image_2,
                R.drawable.image_3, R.drawable.image_4
            ),
            onImageClicked = {imageID: Int ->
                showImageDetail = true
                selectedImageId = imageID
            }
        )

        if(showImageDetail)
        {
            ShowImageInDetail (
                imageID = selectedImageId,
                modifier = Modifier
            ) {
                showImageDetail = false
            }
        }

    }

}

// End of design, rest is implementation


















@Composable
fun ShowImageInDetail(
    @DrawableRes imageID: Int,
    modifier: Modifier = Modifier,
    onDismissRequested: () -> Unit
)
{
    Dialog(onDismissRequest = onDismissRequested) {
        Column {
            Image(
                modifier = modifier,
                painter = painterResource(id = imageID),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Composable
fun ImageSelector(
    images: List<Int>,
    modifier: Modifier = Modifier,
    onManageImagesClick: () -> Unit,
    onImageClicked: (imageID: Int) -> Unit
)
{
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
            ) { onManageImagesClick() }
            .padding(top = 8.dp, bottom = 12.dp, end = 8.dp),
        text = "Images ᐳ",
        style = MaterialTheme.typography.labelLarge
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { imageResource ->
            Image(
                modifier = Modifier.clickable { onImageClicked(imageResource) },
                painter = painterResource(id = imageResource),
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
        }

    }
}
@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    onCategoryClick: () -> Unit,
    categories: List<String>
)
{
    Column (
        modifier = modifier
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                ) { onCategoryClick }
                .padding(top = 8.dp, bottom = 4.dp, end = 8.dp),
            text = "Categories ᐳ",
            style = MaterialTheme.typography.labelLarge
        )
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                ElevatedSuggestionChip(
                    onClick = { /*TODO*/ },
                    label = {
                        Text(text = category)
                    }
                )
            }

        }
    }
}


// Show the difference between the start and end time in a box.
@Composable
fun DurationDisplay(modifier: Modifier = Modifier, startTime: String, endTime: String)
{

    // Convert the times to minutes
    val minutes1 = startTime.split(":")[0].toInt() * 60 + startTime.split(":")[1].toInt()
    val minutes2 = endTime.split(":")[0].toInt() * 60 + endTime.split(":")[1].toInt()

    var hoursBetween = (minutes2 - minutes1).toDouble() / 60.0

    if(minutes2 < minutes1)
    {
        hoursBetween += 24
    }

    // Do not show decimals
    val displayHours = String.format("%.0f", hoursBetween)


    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10),
        tonalElevation = 2.dp
    )
    {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = displayHours,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "Hours Spent",
                style = MaterialTheme.typography.headlineSmall
            )
        }
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