package com.example.cleanarchitecture.domain.repository

import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Resource<List<User>>>

    fun getUserById(id: Int): Flow<Resource<User>>
}
