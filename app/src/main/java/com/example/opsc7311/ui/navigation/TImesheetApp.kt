package com.example.opsc7311.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.opsc7311.ui.models.Timesheet
import com.example.opsc7311.ui.screens.list.TimesheetListScreen
import com.example.opsc7311.ui.screens.timesheet.TimesheetEditScreen
import com.example.opsc7311.ui.screens.timesheet.TimesheetViewModel
import com.example.opsc7311.viewmodels.SharedViewModel

object Screens {
    const val EDIT_WINDOW_KEY = "Edit/{id}"
    const val EDIT_WINDOW_ARGUMENT_KEY = "id"
    const val LIST_WINDOW_KEY = "List"
}

@Composable
fun TimesheetApp(
    sharedViewModel: SharedViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LIST_WINDOW_KEY,
    ) {

        composable(
            route = Screens.LIST_WINDOW_KEY,
        ) {
            TimesheetListScreen(
                onFabClicked = {
                    navController.navigate(
                        route = "Edit/-1"
                    )
                },
                onTimesheetClicked = { id ->
                    //sharedViewModel.DeleteTimesheet(id)
                    navController.navigate(
                        route = "Edit/${id}"
                    )
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = Screens.EDIT_WINDOW_KEY,
            arguments = listOf(
                navArgument(Screens.EDIT_WINDOW_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->

            val id = navBackStackEntry.arguments!!.getInt(Screens.EDIT_WINDOW_ARGUMENT_KEY)
            var timesheet = sharedViewModel.list.find { it.id == id }
            if(timesheet == null) timesheet = Timesheet()

            // hacky way to pass parameter to view model
            // taken from
            // https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments
            // authored by: mlykotom on Oct 12, 2017 at 8:18
            val timesheetViewModel: TimesheetViewModel =
                viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TimesheetViewModel(timesheet) as T
                }
            })

            TimesheetEditScreen(
                timesheetViewModel = timesheetViewModel
            )
        }
    }
}