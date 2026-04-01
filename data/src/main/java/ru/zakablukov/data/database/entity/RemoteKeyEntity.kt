package ru.zakablukov.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_key")
data class RemoteKeyEntity(
    @PrimaryKey val peopleId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
