package com.an.contactsapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.an.contactsapp.R
import com.an.contactsapp.ui.state.ContactUiState
import com.an.contactsapp.ui.theme.ContactsAppTheme
import com.an.contactsapp.ui.viewmodel.ContactsViewModel

@Composable
fun MainApp(viewModel: ContactsViewModel) {
    ContactsAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MainTopAppBar() }
        ) { innerPadding ->

            // Our ui state (that comes from our viewModel)
            val state by viewModel.uiState.collectAsStateWithLifecycle(
                lifecycleOwner = LocalLifecycleOwner.current
            )
            MainAppContent(modifier = Modifier.padding(innerPadding), state = state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    )
}

@Composable
fun MainAppContent(
    modifier: Modifier = Modifier,
    state: ContactUiState
) {
    Box(modifier = modifier
        .fillMaxSize()
    ) {
        // the loader indicator
        AnimatedVisibility(visible = state.loading) {
            LinearProgressIndicator(
                Modifier.fillMaxWidth()
            )
        }
        // the contact list
        ContactsList(
            modifier = Modifier.fillMaxSize(),
            contacts = state.contacts
        )
    }
}
