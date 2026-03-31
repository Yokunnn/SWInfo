package ru.zakablukov.swinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.usecase.GetAllPeopleUseCase
import ru.zakablukov.domain.util.Request
import ru.zakablukov.swinfo.enums.LoadState
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val getAllPeopleUseCase: GetAllPeopleUseCase
) : ViewModel() {

    private val _peopleResult = MutableStateFlow<List<People>>(emptyList())
    val peopleResult: StateFlow<List<People>> = _peopleResult

    private val _peopleLoadState = MutableStateFlow<LoadState?>(null)
    val peopleLoadState: StateFlow<LoadState?> = _peopleLoadState

    fun loadAllPeople() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllPeopleUseCase.invoke().collect { requestState ->
                when (requestState) {
                    is Request.Error -> _peopleLoadState.emit(LoadState.ERROR)
                    is Request.Loading -> _peopleLoadState.emit(LoadState.LOADING)
                    is Request.Success -> {
                        _peopleLoadState.emit(LoadState.SUCCESS)
                        _peopleResult.emit(requestState.data)
                    }
                }
            }
        }
    }
}