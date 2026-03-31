package ru.zakablukov.swinfo.navigation

sealed class AppDestination(val route: String) {
    data object PeopleList : AppDestination("people_list")
    data object PeopleDetails : AppDestination("people_details/{peopleId}") {
        fun createRoute(peopleId: Int) = "people_details/$peopleId"
    }
}