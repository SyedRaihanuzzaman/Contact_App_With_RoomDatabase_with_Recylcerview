package com.raihan.assesment3practical

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactModel(application: Application) : AndroidViewModel(application) {
    private val contactDao: contactDao
    val allContacts: Contact

    init {
        val database = ContactDatabase.getDatabase(application)
        contactDao = database.contactDao()
        allContacts = contactDao.findByRoll()
    }

    fun insert(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.insert(contact)
        }
    }

    fun deleteContact(contactId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            contactDao.deleteAll(contactId)
        }
    }
} {
}