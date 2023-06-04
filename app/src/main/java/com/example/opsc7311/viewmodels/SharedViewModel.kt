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
            Timesheet(id = 0, title = "Work Sheet"),
            Timesheet(id = 1, "Untitled"),
            Timesheet(id = 2, "Maps")
    )
    val list: List<Timesheet> = _list

    fun AddTimesheet(timesheet: Timesheet)
    {
        _list.add(timesheet)
    }

    fun DeleteTimesheet(id: Int)
    {
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