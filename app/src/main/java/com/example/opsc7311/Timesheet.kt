package com.example.opsc7311

import androidx.annotation.DrawableRes
import com.example.opsc7311.util.Constants
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import java.util.Date

data class Timesheet(
    val title: String = "Untitled",
    val date: String = Constants.dateToTextDisplay.format(Date()),
    val startTime: String = Constants.timeToDateDisplay.format(Date()),
    val endTime: String = Constants.timeToDateDisplay.format(Date()),
    val duration: String = "0",
    val calendarState: UseCaseState = UseCaseState(),
    val clockState1: UseCaseState = UseCaseState(),
    val clockState2: UseCaseState = UseCaseState(),
    val showEnterCategoryPopup: Boolean = false,
    val showImageDetailPopup: Boolean = false,
    @DrawableRes val imageId: Int = 0,
    val isNewCategoryTextUnique: Boolean = true,
    val categories: MutableList<String> = mutableListOf(
        "test", "butter", "mash",
        "apples", "cream", "tomato",
        "lemon", "sauce", "night-cream",
        "zebra", "lion", "coffee"),
    val images: MutableList<Int> = mutableListOf(
        R.drawable.image_1, R.drawable.image_2,
        R.drawable.image_3, R.drawable.image_4
    )
)
