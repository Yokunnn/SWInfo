package ru.zakablukov.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.zakablukov.data.database.AppDatabase
import ru.zakablukov.data.mapper.toDomain
import ru.zakablukov.data.mapper.toEntity
import ru.zakablukov.data.mediator.PeopleRemoteMediator
import ru.zakablukov.data.service.PeopleService
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.repository.PeopleRepository
import ru.zakablukov.domain.util.Request
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PeopleRepositoryImpl @Inject constructor(
    private val peopleService: PeopleService,
    private val database: AppDatabase
) : PeopleRepository {

    override fun getPeople(): Flow<PagingData<People>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),
        remoteMediator = PeopleRemoteMediator(peopleService, database),
        pagingSourceFactory = {
            database.peopleDao().getPeople()
        }
    ).flow.map { pagingData -> pagingData.map { it.toDomain() } }

    override fun getPeopleById(id: Int): Flow<Request<People>> {
        return flow {
            val cached = database.peopleDao().getPeopleById(id).first()
            if (cached != null) {
                emit(Request.Success(cached.toDomain()))
            } else {
                emit(Request.Loading())
            }
            try {
                val peopleResponse = peopleService.getPeopleById(id)
                database.peopleDao().upsertPeople(peopleResponse.toEntity())
                emitAll(
                    database.peopleDao().getPeopleById(id)
                        .filterNotNull()
                        .map { Request.Success(it.toDomain()) }
                )
            } catch (e: Exception) {
                if (cached != null) {
                    emit(Request.Error(e))
                } else {
                    emitAll(
                        database.peopleDao().getPeopleById(id)
                            .filterNotNull()
                            .map { Request.Success(it.toDomain()) }
                    )
                }
            }
        }
    }
}