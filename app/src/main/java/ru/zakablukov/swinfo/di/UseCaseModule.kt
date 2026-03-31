package ru.zakablukov.swinfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.zakablukov.domain.repository.PeopleRepository
import ru.zakablukov.domain.usecase.GetAllPeopleUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAllPeopleUseCase(
        peopleRepository: PeopleRepository
    ) = GetAllPeopleUseCase(peopleRepository)
}