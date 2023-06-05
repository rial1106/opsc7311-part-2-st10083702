package com.example.opsc7311.ui.screens.list.appbar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opsc7311.util.DateCalPicker
import com.example.opsc7311.viewmodels.SharedViewModel


@Composable
fun FilterBar(
    sharedViewModel: SharedViewModel,
    filterBarViewModel: FilterBarViewModel
) {

    val filterBarUiState by filterBarViewModel.uiState.collectAsState()

    // Start Date picker
    DateCalPicker(calendarState = filterBarUiState.startDatePickerState,
        onSelectDate = { date->
            filterBarViewModel.updateStartDate(date.toString())
            sharedViewModel.FilterList(
                date.toString(),
                filterBarUiState.endDate
            )
            filterBarViewModel.setStartDateFilterExpanded(false)
            filterBarViewModel.setShowStartDatePicker(false)
        },
        onNegativeClick = {
            filterBarViewModel.setStartDateFilterExpanded(false)
            filterBarViewModel.setShowStartDatePicker(false)
        }
    )

    // End Date picker
    DateCalPicker(calendarState = filterBarUiState.endDatePickerState,
        onSelectDate = { date->
            filterBarViewModel.updateEndDate(date.toString())

            sharedViewModel.FilterList(
                filterBarUiState.startDate,
                date.toString()
            )
            filterBarViewModel.setEndDateFilterExpanded(false)
            filterBarViewModel.setShowEndDatePicker(false)
        },
        onNegativeClick = {
            filterBarViewModel.setEndDateFilterExpanded(false)
            filterBarViewModel.setShowEndDatePicker(false)
        }

    )

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){

        FilterBarDateItem(
            text = filterBarUiState.startDate,
            expanded = filterBarUiState.startDateFilterExpanded
        ) {
            filterBarViewModel.setStartDateFilterExpanded(true)
            filterBarViewModel.setShowStartDatePicker(true)
        }

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        FilterBarDateItem(
            text = filterBarUiState.endDate,
            expanded = filterBarUiState.endDateFilterExpanded
        ) {
            filterBarViewModel.setEndDateFilterExpanded(true)
            filterBarViewModel.setShowEndDatePicker(true)

        }

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        FilterBarItem(
            text = "Reset"
        ) {
            filterBarViewModel.reset()
            sharedViewModel.ResetFilter()
        }
    }



}

@Preview
@Composable
fun PreviewTripleAppBar() {
    FilterBar(
        sharedViewModel = viewModel(),
        filterBarViewModel = viewModel()
    )
}