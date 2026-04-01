package ru.zakablukov.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.zakablukov.data.database.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun upsertAll(remoteKeys: List<RemoteKeyEntity>)
    @Query(SELECT_REMOTEKEY_BY_PEOPLEID)
    suspend fun getRemoteKeyByPeopleId(peopleId: Int): RemoteKeyEntity?
    @Query(DELETE_ALL_REMOTEKEY)
    suspend fun deleteAllRemoteKey()

    companion object {
        private const val SELECT_REMOTEKEY_BY_PEOPLEID = "SELECT * FROM remote_key WHERE peopleId = :peopleId"
        private const val DELETE_ALL_REMOTEKEY = "DELETE FROM remote_key"
    }
}