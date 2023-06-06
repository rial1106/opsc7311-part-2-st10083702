package com.example.opsc7311.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.util.Converters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date


class SharedViewModel : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(SharedViewModelUiState())
    val uiState: StateFlow<SharedViewModelUiState> = _uiState.asStateFlow()

    fun setStartDate(startDate: String)
    {
        _uiState.update { currentState ->
            currentState.copy(startDate = startDate)
        }
        filterList()
    }

    fun setEndDate(endDate: String)
    {
        _uiState.update { currentState ->
            currentState.copy(endDate = endDate)
        }
        filterList()
    }
    private fun filterList() {

        var date1: Date
        var date2: Date
        try {
            date1 = Converters.localDateToDate.parse(_uiState.value.startDate) as Date
            date2 = Converters.localDateToDate.parse(_uiState.value.endDate) as Date
        } catch (e: Exception) {
            date1 = Date(0)
            date2 = Date()
        }

        val result: MutableList<Timesheet> = mutableListOf()

        for (x in _uiState.value.list) {
            val date = Converters.dateToTextDisplay.parse(x.date) as Date
            if (date in date1 .. date2) {
                result.add(x)
            }
        }

        _uiState.value.filteredList.clear()
        for(y in result)
        {
            _uiState.value.filteredList.add(y)
        }

    }

    fun resetFilter()
    {
        _uiState.value.filteredList.clear()
        _uiState.value.filteredList.addAll(_uiState.value.list)

        _uiState.update { currentState ->
            currentState.copy(startDate = "Start Date", endDate = "End Date")
        }
    }

    fun addTimesheet(timesheet: Timesheet) {
        _uiState.value.list.add(timesheet)
        filterList()
    }

    fun editTimesheet(timesheet: Timesheet) {
        val i = _uiState.value.list.indexOfFirst { it.id == timesheet.id }
        if(i >= 0)
        {
            _uiState.value.list[i] = timesheet
            filterList()
        }

    }

    fun deleteTimesheet(id: Int) {
        _uiState.value.list.removeIf { it.id == id }
        filterList()
    }

    fun getTimeSheetOrReturnNew(id: Int): Timesheet
    {
        var timesheet = _uiState.value.list.find { it.id == id }
        if(timesheet == null)
        {
            timesheet = Timesheet()
        }

        return timesheet
    }
}