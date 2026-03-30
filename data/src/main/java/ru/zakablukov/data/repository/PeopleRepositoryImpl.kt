package ru.zakablukov.data.repository

import kotlinx.coroutines.flow.Flow
import ru.zakablukov.data.RequestUtils.requestFlow
import ru.zakablukov.data.mapper.toDomain
import ru.zakablukov.data.service.PeopleService
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.repository.PeopleRepository
import ru.zakablukov.domain.util.Request
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleService: PeopleService
) : PeopleRepository {
    override suspend fun getAllPeople(): Flow<Request<List<People>>> {
        return requestFlow {
            val people = peopleService.getAllPeople()
            people.toDomain()
        }
    }

    override suspend fun getPeopleById(id: Int): Flow<Request<People>> {
        return requestFlow {
            val peopleOne = peopleService.getPeopleById(id)
            peopleOne.toDomain()
        }
    }
}