package com.an.contactsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.an.contactsapp.data.ContactsRepository
import com.an.contactsapp.ui.state.ContactUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    // We are defining a MutableStateFlow for the `ContactUiState` with an
    // initial value of loading = true. So when the app is first launched,
    // a loading screen will be displayed while we fetch the contacts list.
    private val _uiState = MutableStateFlow(ContactUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        // When the ViewModel is first initialized, we update the `ContactUiState`
        // loading state as true while we fetch the contact list from the device.
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            getContacts()
        }
    }

    // Once the contact list is fetched, we sort the list
    // alphabetically and then update the Ui with the contact list
    // and set the loading state as false.
    private fun getContacts() = viewModelScope.launch {
        val contacts = contactsRepository.getContacts().groupBy { contact ->
            contact.displayName.first().toString()
        }
        _uiState.update { it.copy(
            loading = false,
            contacts = contacts
        ) }
    }
}