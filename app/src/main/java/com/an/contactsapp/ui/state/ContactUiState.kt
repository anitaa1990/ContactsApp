package com.an.contactsapp.ui.state

import com.an.contactsapp.model.ContactModel
import java.util.*

data class ContactUiState(
    val loading: Boolean = false,
    val contacts: GroupedContacts = Collections.emptyMap()
)

/**
 * Type aliases provide alternative names for existing types.
 * If the type name is too long you can introduce a different shorter name
 * and use the new one instead. In this example, we've created a typealias
 * for `ContactModel` to convert it into a Map which includes the alphabets
 * as the key and the list of `ContactModel` as the values of the Map.
 */
typealias GroupedContacts = Map<String, List<ContactModel>>