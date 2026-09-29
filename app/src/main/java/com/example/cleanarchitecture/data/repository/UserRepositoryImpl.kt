package com.example.cleanarchitecture.data.repository

import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.data.mapper.toDomain
import com.example.cleanarchitecture.data.remote.UserApi
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.UserRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
) : UserRepository {

    override fun getUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        emit(request { api.getUsers().toDomain() })
    }

    override fun getUserById(id: Int): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        emit(request { api.getUserById(id).toDomain() })
    }

    private suspend fun <T> request(block: suspend () -> T): Resource<T> = try {
        Resource.Success(block())
    } catch (e: HttpException) {
        Resource.Error("Server error (${e.code()}). Please try again.", e)
    } catch (e: IOException) {
        Resource.Error("No internet connection. Check your network and retry.", e)
    }
}
