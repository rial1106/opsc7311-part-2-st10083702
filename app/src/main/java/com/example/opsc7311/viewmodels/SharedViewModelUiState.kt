package com.example.opsc7311.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.opsc7311.ui.models.Timesheet

data class SharedViewModelUiState (
    val list: SnapshotStateList<Timesheet> = DataSource.timesheets.toMutableStateList(),
    val filteredList: SnapshotStateList<Timesheet> = list.toMutableStateList(),
    val startDate: String = "Start Date",
    val endDate: String = "End Date"

)