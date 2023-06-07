package com.example.opsc7311.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.opsc7311.ui.screens.categories.CategoryScreen
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.viewmodels.SharedViewModel

object HomeGraph {
    const val CATEGORY_SCREEN = "Category"
    const val GOALS_SCREEN = "Goals"
}

@Composable
fun HomeNavGraph(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel,
    filterBarViewModel: FilterBarViewModel
)
{
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = Graph.TIMESHEETS
    ) {
        // Do not pass navController as it has its own NavHost.
        // Each NavHost must have its own navHostController
        composable(route = Graph.TIMESHEETS) {
            TimesheetNavGraph(
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
            )
        }
        composable(route = HomeGraph.CATEGORY_SCREEN) {
            CategoryScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
            )
        }
        composable(route = HomeGraph.GOALS_SCREEN) {
            //GoalsScreen(navController = navController)
        }
    }
}