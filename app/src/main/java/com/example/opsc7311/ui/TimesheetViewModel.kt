package com.example.opsc7311.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.opsc7311.Timesheet
import com.example.opsc7311.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class TimesheetViewModel : ViewModel() {

    // UI State
    private val _uiState = MutableStateFlow(Timesheet())
    val uiState: StateFlow<Timesheet> = _uiState.asStateFlow()

    fun updateDate(date: String){
        val dateObject: Date = Constants.localDateToDate.parse(date) as Date
        val newDate: String = Constants.dateToTextDisplay.format(dateObject)
        _uiState.update { currentState ->
            currentState.copy(date = newDate)
        }
    }

    fun updateStartTime(hours: Int, minutes: Int){
        val startTime = hours.toString().padStart(2, '0') +
                ":" +
                minutes.toString().padStart(2, '0')

        val duration: String = calculateDuration(
            startTime = startTime,
            _uiState.value.endTime
        )

        _uiState.update { currentState ->
            currentState.copy(startTime = startTime, duration = duration)
        }
    }

    fun showClock1()
    {
        _uiState.value.clockState1.show()
    }

    fun showClock2()
    {
        _uiState.value.clockState2.show()
    }

    fun showCalendar()
    {
        _uiState.value.calendarState.show()
    }

    fun showEnterCategoryPopup(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(showEnterCategoryPopup = value)
        }
    }

    fun showImageDetailPopup(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(showImageDetailPopup = value)
        }
    }

    fun setImageToShow(imageId: Int)
    {
        _uiState.update { currentState ->
            currentState.copy(imageId = imageId)
        }
    }

    var newCategoryText by mutableStateOf("")
        private set


    private fun setIsAddCategoryTextUnique(value: Boolean)
    {
        _uiState.update { currentState ->
            currentState.copy(isNewCategoryTextUnique = value)
        }
    }
    fun updateAddCategoryText(it: String)
    {
        newCategoryText = it
        if(_uiState.value.categories.contains(newCategoryText))
        {
            setIsAddCategoryTextUnique(false)
        } else {
            setIsAddCategoryTextUnique(true)
        }
    }

    fun addCategory()
    {
        if(!_uiState.value.categories.contains(newCategoryText))
        {
            val categories = _uiState.value.categories
            categories.add(newCategoryText)
            _uiState.update { currentState ->
                currentState.copy(categories = categories)
            }
        } else {
            setIsAddCategoryTextUnique(false)
        }
    }

    fun updateEndTime(hours: Int, minutes: Int){
        val endTime = hours.toString().padStart(2, '0') +
                ":" +
                minutes.toString().padStart(2, '0')

        val duration: String = calculateDuration(
            startTime = _uiState.value.startTime,
            endTime = endTime
        )

        _uiState.update { currentState ->
            currentState.copy(endTime = endTime, duration = duration)
        }
    }

    private fun calculateDuration(startTime: String, endTime: String) : String
    {
        // Convert the times to minutes
        val minutes1 = startTime.split(":")[0].toInt() * 60 + startTime.split(":")[1].toInt()
        val minutes2 = endTime.split(":")[0].toInt() * 60 + endTime.split(":")[1].toInt()

        var hoursBetween = (minutes2 - minutes1).toDouble() / 60.0

        if (minutes2 < minutes1) {
            hoursBetween += 24
        }

        // Do not show decimals
        return String.format("%.0f", hoursBetween)
    }
}