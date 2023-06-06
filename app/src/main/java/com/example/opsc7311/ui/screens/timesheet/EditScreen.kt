package com.example.opsc7311.ui.screens.timesheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.opsc7311.ui.screens.timesheet.appbar.EditBar


@Composable
fun EditScreen(
    editScreenViewModel: EditScreenViewModel,
) {

    val timesheetUiState by editScreenViewModel.uiState.collectAsState()

    EditScreenContent(
        editScreenUiState = timesheetUiState,
        onSelectDate = { date ->
            editScreenViewModel.updateDate(date.toString())
        },
        onStartTimeSelected = { hours, minutes ->
            editScreenViewModel.updateStartTime(hours = hours, minutes = minutes)
        },
        onEndTimeSelected = { hours, minutes ->
            editScreenViewModel.updateEndTime(hours = hours, minutes = minutes)
        },
        onDateDisplayClick = {
            editScreenViewModel.showCalendar()
        },
        onStartTimeDisplayClick = {
            editScreenViewModel.showClock1()
        },
        onEndTimeDisplayClick = {
            editScreenViewModel.showClock2()
        },
        onCategoryViewClick = {
            editScreenViewModel.showEnterCategoryPopup(true)
        },
        onImageClicked = { imageID: Int ->
            editScreenViewModel.showImageDetailPopup(true)
            editScreenViewModel.setImageToShow(imageID)
        },
        onDismissImagePopup = {
            editScreenViewModel.showImageDetailPopup(false)
        },
        newCategoryText = editScreenViewModel.newCategoryText,
        onNewCategoryTextValChanged = {
            editScreenViewModel.updateAddCategoryText(it)
        },
        onDismissAddCategoryPopup = {
            editScreenViewModel.updateAddCategoryText("")
            editScreenViewModel.showEnterCategoryPopup(false)
        },
        onConfirmAddCategoryClicked = {
            editScreenViewModel.addCategory()
            editScreenViewModel.updateAddCategoryText("")
            editScreenViewModel.showEnterCategoryPopup(false)
        }
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimesheetEditScreen(
    editScreenViewModel: EditScreenViewModel,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit
) {
    Scaffold(
        topBar = {
            EditBar(
                editScreenViewModel = editScreenViewModel,
                onBackPressed = onBackPressed,
                onSavePressed = onSavePressed
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            EditScreen(
                editScreenViewModel = editScreenViewModel
            )
        }
    }
}