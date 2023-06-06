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
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.ui.screens.timesheet.EditScreenViewModel
import com.example.opsc7311.ui.screens.timesheet.TimesheetEditScreen
import com.example.opsc7311.util.IdGenerator
import com.example.opsc7311.viewmodels.SharedViewModel

object Screens {
    const val EDIT_WINDOW_KEY = "Edit/{id}"
    const val EDIT_WINDOW_ARGUMENT_KEY = "id"
    const val LIST_WINDOW_KEY = "List"
}

@Composable
fun TimesheetApp(
    sharedViewModel: SharedViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    filterBarViewModel: FilterBarViewModel = viewModel()
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
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
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
            var timesheet = findTimesheet(sharedViewModel, id)
            if (timesheet == null) timesheet = Timesheet()

            // hacky way to pass parameter to view model
            // taken from
            // https://stackoverflow.com/questions/46283981/android-viewmodel-additional-arguments
            // authored by: mlykotom on Oct 12, 2017 at 8:18
            val editScreenViewModel: EditScreenViewModel =
                viewModel(factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return EditScreenViewModel(timesheet) as T
                    }
                })

            TimesheetEditScreen(
                editScreenViewModel = editScreenViewModel,
                onBackPressed = {
                    sharedViewModel.deleteTimesheet(editScreenViewModel.uiState.value.id)
                    navController.navigate(
                        route = "List"
                    ) {
                        popUpTo(0)
                    }
                },
                onSavePressed = {

                    if (editScreenViewModel.uiState.value.id == -1) {
                        sharedViewModel.addTimesheet(
                            Timesheet(
                                id = IdGenerator.getNewId(),
                                title = editScreenViewModel.uiState.value.title,
                                date = editScreenViewModel.uiState.value.date,
                                startTime = editScreenViewModel.uiState.value.startTime,
                                endTime = editScreenViewModel.uiState.value.endTime,
                                categories = editScreenViewModel.uiState.value.categories,
                                images = editScreenViewModel.uiState.value.images
                            )
                        )
                    } else {
                        sharedViewModel.editTimesheet(
                            Timesheet(
                                id = editScreenViewModel.uiState.value.id,
                                title = editScreenViewModel.uiState.value.title,
                                date = editScreenViewModel.uiState.value.date,
                                startTime = editScreenViewModel.uiState.value.startTime,
                                endTime = editScreenViewModel.uiState.value.endTime,
                                categories = editScreenViewModel.uiState.value.categories,
                                images = editScreenViewModel.uiState.value.images
                            )
                        )
                    }

                    navController.navigate(
                        route = "List"
                    ) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

private fun findTimesheet(sharedViewModel: SharedViewModel, id: Int): Timesheet? {
    return sharedViewModel.uiState.value.list.find { it.id == id }
}