package ru.zakablukov.swinfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.zakablukov.swinfo.screen.PeopleListScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navHostController,
        startDestination = AppDestination.PeopleList.route
    ) {
        composable(route = AppDestination.PeopleList.route) {
            PeopleListScreen(
                onPeopleClick = { id ->
                    navHostController.navigate(
                        AppDestination.PeopleDetails.createRoute(id)
                    )
                }
            )
        }
    }
}