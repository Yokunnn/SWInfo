package ru.zakablukov.data.mapper

import ru.zakablukov.data.database.entity.PeopleEntity
import ru.zakablukov.data.database.entity.RemoteKeyEntity
import ru.zakablukov.data.model.PeopleListResponse
import ru.zakablukov.data.model.PeopleResponse
import ru.zakablukov.domain.model.People
import androidx.core.net.toUri

fun PeopleListResponse.toDomain(): List<People> =
    people.map { it.toDomain() }

fun PeopleResponse.toDomain(): People =
    People(
        url.toUri().pathSegments.lastOrNull()?.toInt() ?: -1,
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        films.map { it.toUri().pathSegments.lastOrNull()?.toInt() ?: -1 }
    )

fun PeopleResponse.toRemoteKey(prevKey: Int?, nextKey: Int?): RemoteKeyEntity =
    RemoteKeyEntity(
        url.toUri().pathSegments.lastOrNull()?.toInt() ?: -1,
        prevKey,
        nextKey
    )

fun PeopleResponse.toEntity(): PeopleEntity =
    PeopleEntity(
        url.toUri().pathSegments.lastOrNull()?.toInt() ?: -1,
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        films.map { it.toUri().pathSegments.lastOrNull()?.toInt() ?: -1 }.toString()
    )

fun PeopleEntity.toDomain(): People =
    People(
        id,
        name,
        height,
        mass,
        hairColor,
        skinColor,
        eyeColor,
        birthYear,
        gender,
        filmsId.removePrefix("[").removeSuffix("]").split(", ").map { it.toInt() }
    )