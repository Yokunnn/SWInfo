package ru.zakablukov.swinfo.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {
    @Serializable
    data object PeopleList : AppDestination
    @Serializable
    data class PeopleDetails(val peopleId: Int) : AppDestination
}