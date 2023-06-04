package com.example.opsc7311.ui.models

import com.example.opsc7311.R
import com.example.opsc7311.util.Converters
import java.util.Date

data class Timesheet(
    val id: Int = 0,
    val title: String = "Untitled",
    val date: String = Converters.dateToTextDisplay.format(Date()),
    val startTime: String = Converters.timeToDateDisplay.format(Date()),
    val endTime: String = Converters.timeToDateDisplay.format(Date()),
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
