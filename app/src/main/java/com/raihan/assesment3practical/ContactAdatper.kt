package com.raihan.assesment3practical

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdatper(private val onDeleteClickListener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactAdatper.ContactViewHolder>() {

    private var contactList: List<Contact> = emptyList()

    fun setContacts(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.contactName)
        private val emailTextView: TextView = itemView.findViewById(R.id.contactEmail)
        private val phoneTextView: TextView = itemView.findViewById(R.id.contactPhone)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(contact:Contact) {
            nameTextView.text = contact.name
            emailTextView.text = contact.email
            phoneTextView.text = contact.phone
            deleteButton.setOnClickListener { onDeleteClickListener(contact) }
        }
    }
    }
