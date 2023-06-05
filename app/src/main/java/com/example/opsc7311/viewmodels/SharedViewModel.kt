package com.example.opsc7311.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.opsc7311.R
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.util.Converters
import java.util.Date


class SharedViewModel : ViewModel() {

    private val _list = mutableStateListOf(
        Timesheet(
            id = 0,
            title = "Work Sheet",
            startTime = "03:17",
            endTime = "05:17",
            date = "13 May 2023",
            categories = mutableListOf("eggs", "rice")
        ),
        Timesheet(
            id = 1,
            title = "Park View Construction Project",
            startTime = "01:12", endTime = "12:39",
            date = "24 April 2022", categories = mutableListOf(
                "cranes", "trucks", "ladders",
                "screws", "beads", "traces", "workers"
            ),
            images = mutableListOf(R.drawable.image_1, R.drawable.image_2)
        ),

        Timesheet(id = 3, "Untitled",
            categories = mutableListOf(
                "test", "butter", "mash",
                "apples", "cream", "tomato",
                "lemon", "sauce"
            ),
            images = mutableListOf(R.drawable.image_3, R.drawable.image_4, R.drawable.image_1)
        ),
        Timesheet(
            id = 4, "Maps"
        )
    )
    val list: List<Timesheet> = _list



    private var _filteredList = _list.toMutableStateList()
    val filteredList: List<Timesheet> = _filteredList
    fun FilterList(startDate: String, endDate: String) {

        val date1: Date
        val date2: Date
        try {
            date1 = Converters.localDateToDate.parse(startDate) as Date
            date2 = Converters.localDateToDate.parse(endDate) as Date
        } catch (e: Exception) {
            return
        }

        val result: MutableList<Timesheet> = mutableListOf()

        for (x in _list) {
            val date = Converters.dateToTextDisplay.parse(x.date) as Date
            if (date in date1 .. date2) {
                result.add(x)
            }
        }

        _filteredList.clear()
        for(y in result)
        {
            _filteredList.add(y)
        }

    }

    fun ResetFilter()
    {
        _filteredList.clear()
        _filteredList.addAll(_list)
    }

    fun AddTimesheet(timesheet: Timesheet) {
        _list.add(timesheet)
    }

    fun DeleteTimesheet(id: Int) {
        _list.removeIf { it.id == id }
    }
}