package com.example.cleanarchitecture.domain.usecase

import app.cash.turbine.test
import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private val repository: UserRepository = mockk()
    private lateinit var getUsers: GetUsersUseCase

    private val users = listOf(
        user(id = 1, name = "Zoya", username = "zoya", email = "zoya@mail.com"),
        user(id = 2, name = "Ahmed", username = "ahmed", email = "ahmed@mail.com"),
    )

    @Before
    fun setUp() {
        getUsers = GetUsersUseCase(repository)
    }

    @Test
    fun `emits users sorted by name when query is blank`() = runTest {
        every { repository.getUsers() } returns flowOf(Resource.Loading, Resource.Success(users))

        getUsers().test {
            assertEquals(Resource.Loading, awaitItem())
            val success = awaitItem() as Resource.Success
            assertEquals(listOf("Ahmed", "Zoya"), success.data.map { it.name })
            awaitComplete()
        }
    }

    @Test
    fun `filters users by query`() = runTest {
        every { repository.getUsers() } returns flowOf(Resource.Success(users))

        getUsers(query = "zo").test {
            val success = awaitItem() as Resource.Success
            assertEquals(listOf("Zoya"), success.data.map { it.name })
            awaitComplete()
        }
    }

    @Test
    fun `propagates repository errors`() = runTest {
        every { repository.getUsers() } returns flowOf(Resource.Error("boom"))

        getUsers().test {
            assertEquals(Resource.Error("boom"), awaitItem())
            awaitComplete()
        }
    }

    private fun user(id: Int, name: String, username: String, email: String) = User(
        id = id,
        name = name,
        username = username,
        email = email,
        phone = "",
        website = "",
        city = "",
        company = "",
    )
}
