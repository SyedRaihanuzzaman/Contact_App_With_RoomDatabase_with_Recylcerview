package com.raihan.assesment3practical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raihan.assesment3practical.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var contactViewModel: ContactModel
    private lateinit var contactAdapter: ContactAdatper
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactDatabase: ContactDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        contactDatabase = ContactDatabase.getDatabase(this)

        binding.savebtn.setOnClickListener {
            saveData()
        }
        binding.deleteAllbtn.setOnClickListener {
            GlobalScope.launch {
                contactDatabase.contactDao().deleteAll()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdatper { contact -> deleteContact(contact) }
        recyclerView.adapter = contactAdapter

        contactViewModel = ViewModelProvider(this).get(ContactModel::class.java)

        binding.searchbutton.setOnClickListener {
            val searchQuery = binding.searchbybtnEtxt.text.toString()
            contactViewModel.searchContacts(searchQuery).observe(this, Observer { contacts ->
                contactAdapter.setContacts(contacts)
            })
        }

        deleteButton.setOnClickListener {
            // Implement code to delete the selected contact
        }

        // Load all contacts when the activity starts
        contactViewModel.getAllContacts().observe(this, Observer { contacts ->
            contactAdapter.setContacts(contacts)
        })
    }


    private fun saveData() {

        val name = binding.nameEtxt.text.toString()
        val email = binding.emailEtxt.text.toString()
        val phoneNo = binding.phonnoEtxt.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && phoneNo.isNotEmpty()) {
            val student = Contact(null, name, email, phoneNo.toInt())
            GlobalScope.launch(Dispatchers.IO) {
                contactDatabase.contactDao().insert(student)
            }
            binding.nameEtxt.text.clear()
            binding.emailEtxt.text.clear()
            binding.phonnoEtxt.text.clear()

            Toast.makeText(this@MainActivity, "Data saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Please enter all the data", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun deleteContact(contact: Contact) {
        contactViewModel.delete(contact)
    }
}


