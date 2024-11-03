package com.example.contactmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactmanager.database.ContactsDatabaseHelper
import com.example.contactmanager.databinding.ActivityMainBinding
import com.example.contactmanager.models.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: ContactsDatabaseHelper
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter
    private var contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = ContactsDatabaseHelper(this)
        loadContacts()

        binding.buttonAddContact.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intent, 100)
        }

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val contact = contactList[position]
            val intent = Intent(this, ContactDetailActivity::class.java)
            intent.putExtra("CONTACT_ID", contact.id)
            startActivity(intent)
        }
    }

    private fun loadContacts() {
        contactList = databaseHelper.getAllContacts().toMutableList()
        contactAdapter = ContactAdapter(this, contactList)
        binding.listViewContacts.adapter = contactAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            // Cập nhật ListView sau khi thêm danh bạ mới
            contactList.clear()
            contactList.addAll(databaseHelper.getAllContacts())
            loadContacts()
            contactAdapter.notifyDataSetChanged()
        }
    }
}