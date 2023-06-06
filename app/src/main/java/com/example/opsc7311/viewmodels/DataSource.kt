package com.example.opsc7311.viewmodels

import com.example.opsc7311.R
import com.example.opsc7311.ui.models.Timesheet

object DataSource {
    val timesheets = mutableListOf(
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
}