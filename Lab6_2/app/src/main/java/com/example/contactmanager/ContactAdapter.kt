package com.example.contactmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.contactmanager.databinding.ContactItemBinding
import com.example.contactmanager.models.Contact

class ContactAdapter(private val context: Context, private val contacts: List<Contact>) : BaseAdapter() {

    override fun getCount(): Int = contacts.size

    override fun getItem(position: Int): Any = contacts[position]

    override fun getItemId(position: Int): Long = contacts[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ContactItemBinding
        val view: View

        if (convertView == null) {
            binding = ContactItemBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ContactItemBinding
            view = convertView
        }

        val contact = contacts[position]
        binding.textViewName.text = contact.name
        binding.textViewPhone.text = contact.phone
        binding.textViewEmail.text = contact.email

        return view
    }
}