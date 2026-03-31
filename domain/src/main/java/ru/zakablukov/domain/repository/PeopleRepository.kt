package ru.zakablukov.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.util.Request

interface PeopleRepository {
    fun getPeople(): Flow<PagingData<People>>
    suspend fun getPeopleById(id: Int): Flow<Request<People>>
}