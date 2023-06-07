package com.example.opsc7311.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.opsc7311.ui.navigation.Graph
import com.example.opsc7311.ui.navigation.HomeGraph
import com.example.opsc7311.ui.navigation.HomeNavGraph
import com.example.opsc7311.ui.navigation.TimesheetNavGraph
import com.example.opsc7311.ui.screens.list.appbar.FilterBarViewModel
import com.example.opsc7311.viewmodels.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController())
{
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { PaddingValues->
        Box(
            modifier = Modifier.padding(PaddingValues)
        ){
            val sharedViewModel: SharedViewModel = viewModel()
            val filterBarViewModel: FilterBarViewModel = viewModel()

            HomeNavGraph(
                navController = navController,
                sharedViewModel = sharedViewModel,
                filterBarViewModel = filterBarViewModel
            )
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            label = { Text("Timesheets") },
            selected = currentDestination?.hierarchy?.any { it.route == Graph.TIMESHEETS } == true,
            onClick = {
                 Log.d("aaaaaaa", currentDestination?.hierarchy?.toList().toString())
                navController.navigate(route = Graph.TIMESHEETS)
            },
            alwaysShowLabel = false
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            label = { Text("Categories") },
            selected = currentDestination?.hierarchy?.any { it.route == HomeGraph.CATEGORY_SCREEN } == true,
            onClick = {
                Log.d("aaaaaaa", currentDestination?.hierarchy?.toList().toString())
                navController.navigate(route = HomeGraph.CATEGORY_SCREEN)
            },
            alwaysShowLabel = false
        )
    }
}