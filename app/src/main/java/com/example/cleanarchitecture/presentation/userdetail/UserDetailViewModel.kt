package com.example.cleanarchitecture.presentation.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.usecase.GetUserByIdUseCase
import com.example.cleanarchitecture.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserById: GetUserByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle[Screen.ARG_USER_ID])

    private val _state = MutableStateFlow(UserDetailState())
    val state: StateFlow<UserDetailState> = _state.asStateFlow()

    init {
        loadUser()
    }

    fun onRetry() = loadUser()

    private fun loadUser() {
        getUserById(userId)
            .onEach { resource ->
                _state.value = when (resource) {
                    is Resource.Loading -> _state.value.copy(isLoading = true, error = null)
                    is Resource.Success -> _state.value.copy(
                        isLoading = false,
                        user = resource.data,
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
