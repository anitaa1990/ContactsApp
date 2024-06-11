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
    private val _uiState = MutableStateFlow(ContactUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            getContacts()
        }
    }
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