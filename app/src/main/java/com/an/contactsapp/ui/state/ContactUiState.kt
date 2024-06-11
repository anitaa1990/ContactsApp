package com.an.contactsapp.ui.state

import com.an.contactsapp.model.ContactModel
import java.util.*

data class ContactUiState(
    val loading: Boolean = false,
    val contacts: GroupedContacts = Collections.emptyMap()
)

typealias GroupedContacts = Map<String, List<ContactModel>>