package ru.zakablukov.swinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.zakablukov.domain.model.People
import ru.zakablukov.domain.usecase.GetPeopleByIdUseCase
import ru.zakablukov.domain.util.Request
import ru.zakablukov.swinfo.enums.LoadState
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val getPeopleByIdUseCase: GetPeopleByIdUseCase
) : ViewModel() {

    private val _peopleResult = MutableStateFlow<People?>(null)
    val peopleResult: StateFlow<People?> = _peopleResult

    private val _peopleLoadState = MutableStateFlow<LoadState?>(null)
    val peopleLoadState: StateFlow<LoadState?> = _peopleLoadState

    fun loadPeopleById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPeopleByIdUseCase.invoke(id).collect { requestState ->
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