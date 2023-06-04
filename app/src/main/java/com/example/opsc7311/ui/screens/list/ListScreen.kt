package com.example.opsc7311.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.opsc7311.viewmodels.SharedViewModel

@Composable
fun TimesheetList(
    sharedViewModel: SharedViewModel,
    onTimesheetClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
)
{

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        items(
            items = sharedViewModel.list,
            key = { timesheet ->
                // Return a stable + unique key for the item
                timesheet.id
            }
        ) {timeSheet->
            
            TimesheetListItem(
                timeSheet = timeSheet,
                onTimesheetClicked = onTimesheetClicked
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimesheetListScreen(
    onFabClicked: () -> Unit,
    onTimesheetClicked: (Int) -> Unit,
    sharedViewModel: SharedViewModel
)
{

    Scaffold(
        topBar = {},
        floatingActionButton = {
            ListFab(onFabClicked = onFabClicked)
        }
    ) {
        TimesheetList(
            sharedViewModel = sharedViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            onTimesheetClicked = onTimesheetClicked
        )
    }
}

@Composable
fun ListFab(
    onFabClicked: () -> Unit
)
{
    FloatingActionButton(
        onClick = onFabClicked,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}


@Preview
@Composable
fun ListPreview()
{
    TimesheetListScreen(
        onFabClicked = {},
        onTimesheetClicked = {},
        sharedViewModel = viewModel()
    )
}