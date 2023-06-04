package com.example.opsc7311.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.opsc7311.ui.screens.timesheet.TimesheetEditScreen
import com.example.opsc7311.ui.screens.list.TimesheetListScreen
import com.example.opsc7311.viewmodels.SharedViewModel

object Screens {
    const val ADD_WINDOW_KEY = "Add/{id}"
    const val ADD_WINDOW_ARGUMENT_KEY = "id"
    const val LIST_WINDOW_KEY = "List"
}

@Composable
fun TimesheetApp(
    sharedViewModel: SharedViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
)
{
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
                        route = "Add/-1"
                    )
                },
                onTimesheetClicked = {id->
                    sharedViewModel.DeleteTimesheet(id)
                    /*navController.navigate(
                        route = "Add/${id}"
                    )*/
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = Screens.ADD_WINDOW_KEY,
            arguments = listOf(
                navArgument(Screens.ADD_WINDOW_ARGUMENT_KEY) {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments!!.getInt("id")
            TimesheetEditScreen(id)
        }
    }
}