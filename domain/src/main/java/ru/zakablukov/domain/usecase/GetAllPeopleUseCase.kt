package ru.zakablukov.domain.usecase

import ru.zakablukov.domain.repository.PeopleRepository

class GetAllPeopleUseCase(
    val peopleRepository: PeopleRepository
) {
    suspend fun invoke() = peopleRepository.getAllPeople()
}