package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(id: Int): Flow<Resource<User>> = repository.getUserById(id)
}
