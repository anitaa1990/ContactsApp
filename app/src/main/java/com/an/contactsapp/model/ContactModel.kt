package com.an.contactsapp.model

data class ContactModel(
    val id: String,
    val displayName: String,
    val phoneNumber: String,
    val photoThumbnailUri: String?,
    val photoUri: String?
)
