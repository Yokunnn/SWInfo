package ru.zakablukov.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ru.zakablukov.data.database.AppDatabase
import ru.zakablukov.data.database.entity.PeopleEntity
import ru.zakablukov.data.database.entity.RemoteKeyEntity
import ru.zakablukov.data.mapper.toEntity
import ru.zakablukov.data.mapper.toRemoteKey
import ru.zakablukov.data.service.PeopleService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PeopleRemoteMediator @Inject constructor(
    private val peopleService: PeopleService,
    private val database: AppDatabase
) : RemoteMediator<Int, PeopleEntity>() {

    private val peopleDao = database.peopleDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PeopleEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKey?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextKey
                }
            }
            val people = peopleService.getPeople(page).people
            val endOfPaginationReached = people.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteAllRemoteKey()
                    peopleDao.deleteAllPeople()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = people.map { it.toRemoteKey(prevKey, nextKey) }
                val peopleEntities = people.map { it.toEntity() }

                peopleDao.upsertAll(peopleEntities)
                remoteKeyDao.upsertAll(remoteKeys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PeopleEntity>): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { people -> remoteKeyDao.getRemoteKeyByPeopleId(people.id) }
    }
}