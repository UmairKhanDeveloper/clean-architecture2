package com.example.cleanarchitecture.presentation.users

import com.example.cleanarchitecture.core.Resource
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.domain.repository.UserRepository
import com.example.cleanarchitecture.domain.usecase.GetUsersUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository: UserRepository = mockk()

    private val users = listOf(
        User(1, "Ahmed", "ahmed", "ahmed@mail.com", "", "", "Lahore", "Acme"),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads users on init`() = runTest(dispatcher) {
        every { repository.getUsers() } returns flowOf(Resource.Loading, Resource.Success(users))

        val viewModel = UserListViewModel(GetUsersUseCase(repository))
        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
        assertEquals(users, viewModel.state.value.users)
        assertEquals(null, viewModel.state.value.error)
    }

    @Test
    fun `exposes error message when loading fails`() = runTest(dispatcher) {
        every { repository.getUsers() } returns flowOf(Resource.Error("no internet"))

        val viewModel = UserListViewModel(GetUsersUseCase(repository))
        advanceUntilIdle()

        assertEquals("no internet", viewModel.state.value.error)
    }

    @Test
    fun `query change filters users`() = runTest(dispatcher) {
        every { repository.getUsers() } returns flowOf(Resource.Success(users))

        val viewModel = UserListViewModel(GetUsersUseCase(repository))
        advanceUntilIdle()

        viewModel.onQueryChange("zzz")
        advanceUntilIdle()

        assertEquals(emptyList<User>(), viewModel.state.value.users)
    }
}
