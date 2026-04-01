package ru.zakablukov.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.zakablukov.data.database.entity.PeopleEntity

@Dao
interface PeopleDao {
    @Upsert
    suspend fun upsertAll(people: List<PeopleEntity>)
    @Upsert
    suspend fun upsertPeople(people: PeopleEntity)
    @Query(SELECT_PEOPLE)
    fun getPeople(): PagingSource<Int, PeopleEntity>
    @Query(SELECT_PEOPLE_BY_ID)
    fun getPeopleById(id: Int): Flow<PeopleEntity?>
    @Query(DELETE_ALL_PEOPLE)
    suspend fun deleteAllPeople()

    companion object {
        private const val SELECT_PEOPLE = "SELECT * FROM people"
        private const val SELECT_PEOPLE_BY_ID = "SELECT * FROM people WHERE id = :id"
        private const val DELETE_ALL_PEOPLE = "DELETE FROM people"
    }
}