package ru.zakablukov.data.service

import retrofit2.http.GET
import ru.zakablukov.data.model.PeopleListResponse

interface PeopleService {

    @GET(GET_ALL_PEOPLE_REQUEST)
    suspend fun getAllPeople() : PeopleListResponse

    companion object {
        private const val GET_ALL_PEOPLE_REQUEST = "people/"
    }
}