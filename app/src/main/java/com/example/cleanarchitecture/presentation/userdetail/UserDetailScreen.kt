package com.example.cleanarchitecture.presentation.userdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanarchitecture.domain.model.User
import com.example.cleanarchitecture.presentation.common.ErrorState
import com.example.cleanarchitecture.presentation.common.LoadingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = state.user?.name ?: "User") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        val user = state.user
        when {
            state.isLoading && user == null -> LoadingState(Modifier.padding(innerPadding))
            state.error != null -> ErrorState(
                message = requireNotNull(state.error),
                onRetry = viewModel::onRetry,
                modifier = Modifier.padding(innerPadding),
            )

            user != null -> UserDetailContent(
                user = user,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
private fun UserDetailContent(user: User, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DetailRow(label = "Username", value = user.username)
        DetailRow(label = "Email", value = user.email)
        DetailRow(label = "Phone", value = user.phone)
        DetailRow(label = "Website", value = user.website)
        DetailRow(label = "City", value = user.city)
        DetailRow(label = "Company", value = user.company)
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Text(text = value.ifBlank { "-" }, style = MaterialTheme.typography.bodyLarge)
    }
}
