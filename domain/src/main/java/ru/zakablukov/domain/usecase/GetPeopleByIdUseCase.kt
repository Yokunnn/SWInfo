package ru.zakablukov.domain.usecase

import ru.zakablukov.domain.repository.PeopleRepository

class GetPeopleByIdUseCase(
    val peopleRepository: PeopleRepository
) {
    fun invoke(id: Int) = peopleRepository.getPeopleById(id)
}