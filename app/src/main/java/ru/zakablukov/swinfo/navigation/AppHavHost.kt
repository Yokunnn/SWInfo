package ru.zakablukov.swinfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ru.zakablukov.swinfo.screen.PeopleDetailsScreen
import ru.zakablukov.swinfo.screen.PeopleListScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navHostController,
        startDestination = AppDestination.PeopleList
    ) {
        composable<AppDestination.PeopleList> {
            PeopleListScreen(
                onPeopleClick = { id ->
                    navHostController.navigate(
                        AppDestination.PeopleDetails(id)
                    )
                }
            )
        }
        composable<AppDestination.PeopleDetails> {
            val id = it.toRoute<AppDestination.PeopleDetails>().peopleId
            PeopleDetailsScreen(
                id,
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}