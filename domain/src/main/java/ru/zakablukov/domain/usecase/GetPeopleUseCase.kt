package ru.zakablukov.domain.usecase

import ru.zakablukov.domain.repository.PeopleRepository

class GetPeopleUseCase(
    private val peopleRepository: PeopleRepository
) {
    operator fun invoke() = peopleRepository.getPeople()
}