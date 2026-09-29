package com.example.cleanarchitecture.domain.usecase

import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(query: String = ""): Flow<Resource<List<User>>> =
        repository.getUsers().map { resource ->
            when (resource) {
                is Resource.Success -> Resource.Success(resource.data.filterBy(query))
                else -> resource
            }
        }

    private fun List<User>.filterBy(query: String): List<User> {
        if (query.isBlank()) return sortedBy { it.name }
        return filter {
            it.name.contains(query, ignoreCase = true) ||
                it.username.contains(query, ignoreCase = true) ||
                it.email.contains(query, ignoreCase = true)
        }.sortedBy { it.name }
    }
}
