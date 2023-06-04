package com.example.opsc7311.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCalPicker(
    calendarState: UseCaseState,
    onSelectDate: (LocalDate) -> Unit
) {
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date(onSelectDate = onSelectDate)
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