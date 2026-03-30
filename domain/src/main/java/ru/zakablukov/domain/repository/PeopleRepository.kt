package ru.zakablukov.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.util.Request

interface PeopleRepository {
    suspend fun getAllPeople(): Flow<Request<List<People>>>
    suspend fun getPeopleById(id: Int): Flow<Request<People>>
}