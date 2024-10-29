package com.example.contactmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactmanager.database.ContactsDatabaseHelper
import com.example.contactmanager.databinding.ActivityContactDetailBinding
import com.example.contactmanager.models.Contact

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding
    private lateinit var databaseHelper: ContactsDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = ContactsDatabaseHelper(this)

        // Lấy ID của liên hệ từ Intent
        val contactId = intent.getIntExtra("CONTACT_ID", -1)
        if (contactId != -1) {
            loadContactDetails(contactId)
        }

        // Xử lý nút xóa liên hệ
        binding.buttonDeleteContact.setOnClickListener {
            databaseHelper.deleteContact(contactId)
            finish()
        }
    }

    private fun loadContactDetails(contactId: Int) {
        val contact = databaseHelper.getContactById(contactId)
        if (contact != null) {
            binding.textViewName.text = contact.name
            binding.textViewPhone.text = contact.phone
            binding.textViewEmail.text = contact.email
        }
    }
}
