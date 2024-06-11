package com.an.contactsapp.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.an.contactsapp.R

@Composable
fun PermissionDialog(
    onDismissRequest: () -> Unit = {},
    onPermissionRequest: () -> Unit = {}
) {
    AlertDialog(
        icon = {
            Icon(Icons.Filled.Warning, contentDescription = "Warn Icon", tint = MaterialTheme.colorScheme.primary)
        },
        title = {
            Text(text = stringResource(id = R.string.permission_title))
        },
        text = {
            Text(text = stringResource(id = R.string.permission_desc))
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = { onPermissionRequest() }
            ) {
                Text(stringResource(id = R.string.settings), color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(stringResource(id = R.string.dismiss), color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}