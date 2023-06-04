package com.example.opsc7311.ui.screens.timesheet

import android.util.Log
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opsc7311.util.DateCalPicker
import com.example.opsc7311.util.TimeClockPicker
import com.example.opsc7311.ui.theme.Opsc7311Theme


@Composable
fun TimesheetEditScreen(id: Int, timesheetViewModel: TimesheetViewModel = viewModel()) {

    Log.d("aaaaaa", id.toString())

    val timesheetUiState by timesheetViewModel.uiState.collectAsState()

    // Calendar picker
    DateCalPicker(calendarState = timesheetUiState.calendarState,
        onSelectDate = {date-> timesheetViewModel.updateDate(date.toString())}
    )

    // First time picker
    TimeClockPicker(clockState = timesheetUiState.clockState1, onTimeSelected = { hours, minutes ->
        timesheetViewModel.updateStartTime(hours = hours, minutes = minutes)
    })

    // Second time picker
    TimeClockPicker(clockState = timesheetUiState.clockState2, onTimeSelected = { hours, minutes ->
        timesheetViewModel.updateEndTime(hours = hours, minutes = minutes)
    })

    // Show the column of pickers and dates
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        DateDisplay(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            date = timesheetUiState.date,
            onIconClicked = {
                timesheetViewModel.showCalendar()
            }
        )
        TimeDisplay(
            title = "Start Time",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            time = timesheetUiState.startTime,
            onIconClicked = {
                timesheetViewModel.showClock1()
            }
        )
        TimeDisplay(
            title = "End Time",
            modifier = Modifier
                .fillMaxWidth(),
            time = timesheetUiState.endTime,
            onIconClicked = {
                timesheetViewModel.showClock2()
            }
        )

        DurationDisplay(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 28.dp)
                .height(120.dp)
                .width(160.dp),
            duration = timesheetUiState.duration
        )

        CategoryView(
            modifier = Modifier,
            categories = timesheetUiState.categories
        ) {
            timesheetViewModel.showEnterCategoryPopup(true)
        }

        Gallery(
            modifier = Modifier
                .height(160.dp)
                .padding(top = 4.dp),
            onManageImagesClick = {},
            images = timesheetUiState.images,
            onImageClicked = { imageID: Int ->
                timesheetViewModel.showImageDetailPopup(true)
                timesheetViewModel.setImageToShow(imageID)
            }
        )

        if (timesheetUiState.showImageDetailPopup) {
            ImagePopup(
                imageID = timesheetUiState.imageId,
                modifier = Modifier
            ) {
                timesheetViewModel.showImageDetailPopup(false)
            }
        }


        if (timesheetUiState.showEnterCategoryPopup) {
            AddCategoryPopup(
                text = timesheetViewModel.newCategoryText,
                isUnique = timesheetUiState.isNewCategoryTextUnique,
                onValueChanged = {
                    timesheetViewModel.updateAddCategoryText(it)
                },
                onDismissRequested = {
                    timesheetViewModel.updateAddCategoryText("")
                    timesheetViewModel.showEnterCategoryPopup(false)
                }
            ) {
                timesheetViewModel.addCategory()
                timesheetViewModel.updateAddCategoryText("")
                timesheetViewModel.showEnterCategoryPopup(false)
            }
        }

    }

}

// End of design, rest is implementation






@Composable
fun AddCategoryPopup(
    text: String,
    isUnique: Boolean,
    onValueChanged: (it: String) -> Unit,
    onDismissRequested: () -> Unit,
    onConfirmClicked: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismissRequested,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        title = { Text("Enter a category") },
        text = {
            TextField(
                value = text,
                onValueChange = onValueChanged,
                singleLine = true,
                label = { Text(text = "Name") },
                supportingText = {
                    if(!isUnique)
                    {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "There is already a category with this name!"
                        )
                    }
                }
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmClicked,
                enabled = isUnique
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequested) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ImagePopup(
    @DrawableRes imageID: Int,
    modifier: Modifier = Modifier,
    onDismissRequested: () -> Unit
) {
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
fun Gallery(
    images: MutableList<Int>,
    modifier: Modifier = Modifier,
    onManageImagesClick: () -> Unit,
    onImageClicked: (imageID: Int) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
            ) { onManageImagesClick() }
            .padding(top = 8.dp, bottom = 8.dp),
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
fun CategoryView(
    modifier: Modifier = Modifier,
    categories: MutableList<String>,
    onCategoryClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                ) { onCategoryClick.invoke() }
                .padding(top = 8.dp, bottom = 8.dp),
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
fun DurationDisplay(modifier: Modifier = Modifier, duration: String) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10),
        tonalElevation = 2.dp
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = duration,
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

@Composable
fun DateDisplay(
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
fun TimeDisplay(
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
fun TimesheetPreview() {
    Opsc7311Theme {
        TimesheetEditScreen(-1)
    }
}