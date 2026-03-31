package ru.zakablukov.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.zakablukov.data.mapper.toDomain
import ru.zakablukov.data.service.PeopleService
import ru.zakablukov.domain.model.People
import javax.inject.Inject

class PeoplePagingSource @Inject constructor(
    private val peopleService: PeopleService
) : PagingSource<Int, People>() {

    override fun getRefreshKey(state: PagingState<Int, People>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {
        return try {
            val page = params.key ?: 1
            val response = peopleService.getPeople(
                page
            )
            val people = response.toDomain()
            LoadResult.Page(
                people,
                if (page == 1) null else page - 1,
                if (people.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}