package com.example.opsc7311.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate


@Composable
fun ImageRow(
    images: MutableList<Int>,
    onImageClicked: (imageID: Int) -> Unit,
    modifier: Modifier = Modifier
)
{
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalPicker(
    calendarState: UseCaseState,
    onNegativeClick: () -> Unit,
    onSelectDate: (LocalDate) -> Unit
) {
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date(
            onSelectDate = onSelectDate,
            onNegativeClick = onNegativeClick
        )
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