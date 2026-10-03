package com.example.feature_contacts.presentation.contacts

sealed interface ContactsEvent {
    data object ShowError: ContactsEvent
}