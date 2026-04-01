package ru.zakablukov.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.zakablukov.data.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "StarWarsDatabase"
    ).build()

    @Singleton
    @Provides
    fun providePeopleDao(db: AppDatabase) = db.peopleDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(db: AppDatabase) = db.remoteKeyDao()
}