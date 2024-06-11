package com.an.contactsapp.ui.screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.an.contactsapp.R
import com.an.contactsapp.ui.component.PermissionDialog
import com.an.contactsapp.ui.state.ContactUiState
import com.an.contactsapp.ui.theme.ContactsAppTheme
import com.an.contactsapp.ui.viewmodel.ContactsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainApp() {
    ContactsAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MainTopAppBar() }
        ) { innerPadding ->
            val showMainContent = remember { mutableStateOf(false) }
            val showRationale = remember { mutableStateOf(false) }

            val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)
            val requestPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted -> showMainContent.value = isGranted }

            when {
                permissionState.status.isGranted -> {
                    showMainContent.value = true
                    showRationale.value = false
                }
                !permissionState.status.isGranted && permissionState.status.shouldShowRationale -> showRationale.value = true
                else -> {
                    LaunchedEffect(permissionState) {
                        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                    }
                }
            }

            if (showRationale.value) {
                val context = LocalContext.current
                PermissionDialog(
                    onPermissionRequest = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(intent)
                        showRationale.value = false
                    },
                    onDismissRequest = { showRationale.value = false }
                )
            }

            if(showMainContent.value) {
                PermissionGrantedScreen(Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
private fun PermissionGrantedScreen(
    modifier: Modifier
) {
    val viewModel: ContactsViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current
    )
    MainAppContent(modifier = modifier, state = state)
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
