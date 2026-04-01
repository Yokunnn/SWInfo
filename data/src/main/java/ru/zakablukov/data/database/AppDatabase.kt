package ru.zakablukov.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zakablukov.data.database.dao.PeopleDao
import ru.zakablukov.data.database.dao.RemoteKeyDao
import ru.zakablukov.data.database.entity.PeopleEntity
import ru.zakablukov.data.database.entity.RemoteKeyEntity

@Database(
    entities = [
        PeopleEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}