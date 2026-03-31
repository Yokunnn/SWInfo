package ru.zakablukov.swinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.usecase.GetPeopleUseCase
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    getPeopleUseCase: GetPeopleUseCase
) : ViewModel() {

    val peopleResult: Flow<PagingData<People>> = getPeopleUseCase.invoke()
        .cachedIn(viewModelScope)

}