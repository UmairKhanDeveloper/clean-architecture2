package com.example.cleanarchitecture.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsers: GetUsersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UserListState())
    val state: StateFlow<UserListState> = _state.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadUsers()
    }

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query)
        loadUsers()
    }

    fun onRetry() = loadUsers()

    private fun loadUsers() {
        loadJob?.cancel()
        loadJob = getUsers(_state.value.query)
            .onEach { resource ->
                _state.value = when (resource) {
                    is Resource.Loading -> _state.value.copy(isLoading = true, error = null)
                    is Resource.Success -> _state.value.copy(
                        isLoading = false,
                        users = resource.data,
                        error = null,
                    )

                    is Resource.Error -> _state.value.copy(
                        isLoading = false,
                        error = resource.message,
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
