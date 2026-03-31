package ru.zakablukov.domain.usecase

import ru.zakablukov.domain.repository.PeopleRepository

class GetAllPeopleUseCase(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke() = peopleRepository.getAllPeople()
}