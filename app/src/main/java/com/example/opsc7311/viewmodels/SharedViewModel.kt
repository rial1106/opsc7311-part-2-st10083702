package com.example.opsc7311.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.opsc7311.ui.models.Timesheet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {

    private val _list = mutableStateListOf(
        Timesheet(
            id = 0, title = "Work Sheet",
            startTime = "03:17", endTime = "05:17",
            date = "13 May 2023", categories = mutableListOf("eggs", "rice")
        ),
        Timesheet(
            id = 1, title = "Park View Construction Project",
            startTime = "01:12", endTime = "12:39",
            date = "24 April 2022", categories = mutableListOf("cranes", "trucks",
            "ladders", "screws", "beads", "traces", "workers")
        ),

        Timesheet(id = 3, "Untitled", categories = mutableListOf()),
        Timesheet(id = 4, "Maps")
    )
    val list: List<Timesheet> = _list

    fun AddTimesheet(timesheet: Timesheet) {
        _list.add(timesheet)
    }

    fun DeleteTimesheet(id: Int) {
        _list.removeIf { it.id == id }
    }

    /*    fun DeleteTimesheet(timesheet: Timesheet)
        {
            timesheets.remove(timesheet)
        }

        fun EditTimesheet(timesheet: Timesheet)
        {
            val index = timesheets.indexOf(timesheet)
            timesheets[index] = timesheet
        }*/

}