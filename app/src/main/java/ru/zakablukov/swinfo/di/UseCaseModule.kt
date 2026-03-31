package ru.zakablukov.swinfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.zakablukov.domain.repository.PeopleRepository
import ru.zakablukov.domain.usecase.GetPeopleUseCase
import ru.zakablukov.domain.usecase.GetPeopleByIdUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPeopleUseCase(
        peopleRepository: PeopleRepository
    ) = GetPeopleUseCase(peopleRepository)

    @Singleton
    @Provides
    fun provideGetPeopleByIdUseCase(
        peopleRepository: PeopleRepository
    ) = GetPeopleByIdUseCase(peopleRepository)
}