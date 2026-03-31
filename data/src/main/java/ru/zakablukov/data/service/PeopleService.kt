package ru.zakablukov.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.zakablukov.data.model.PeopleListResponse
import ru.zakablukov.data.model.PeopleResponse

interface PeopleService {

    @GET(GET_ALL_PEOPLE_REQUEST)
    suspend fun getPeople(
        @Query(QUERY_PAGE) page: Int
    ) : PeopleListResponse

    @GET(GET_PEOPLE_BY_ID_REQUEST)
    suspend fun getPeopleById(
        @Path(QUERY_ID) id: Int
    ) : PeopleResponse

    companion object {
        private const val GET_ALL_PEOPLE_REQUEST = "people/"
        private const val GET_PEOPLE_BY_ID_REQUEST = "people/{id}"
        private const val QUERY_ID = "id"
        private const val QUERY_PAGE = "page"
    }
}