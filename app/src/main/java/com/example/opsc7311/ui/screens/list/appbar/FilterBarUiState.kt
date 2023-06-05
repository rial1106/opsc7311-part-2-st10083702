package com.example.opsc7311.ui.screens.list.appbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.opsc7311.util.Converters
import java.util.Date

data class FilterBarUiState(
    val startDate: String = "Start Date",
    val endDate: String = "End Date",
    val startDateFilterExpanded: Boolean = false,
    val endDateFilterExpanded: Boolean = false,
)
