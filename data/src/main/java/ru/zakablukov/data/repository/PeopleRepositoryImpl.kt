package ru.zakablukov.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zakablukov.data.RequestUtils.requestFlow
import ru.zakablukov.data.mapper.toDomain
import ru.zakablukov.data.pagingsource.PeoplePagingSource
import ru.zakablukov.data.service.PeopleService
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.repository.PeopleRepository
import ru.zakablukov.domain.util.Request
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peoplePagingSource: PeoplePagingSource,
    private val peopleService: PeopleService
) : PeopleRepository {

    override fun getPeople(): Flow<PagingData<People>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            peoplePagingSource
        }
    ).flow

    override suspend fun getPeopleById(id: Int): Flow<Request<People>> {
        return requestFlow {
            val peopleOne = peopleService.getPeopleById(id)
            peopleOne.toDomain()
        }
    }
}