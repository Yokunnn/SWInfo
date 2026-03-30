package ru.zakablukov.data.mapper

import android.net.Uri
import ru.zakablukov.data.model.PeopleListResponse
import ru.zakablukov.data.model.PeopleResponse
import ru.zakablukov.domain.model.People

fun PeopleListResponse.toDomain(): List<People> =
    people.map { it.toDomain() }

fun PeopleResponse.toDomain(): People =
    People(
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        films.map { Uri.parse(it).pathSegments.lastOrNull()?.toInt() ?: -1 }
    )