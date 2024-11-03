package com.example.contactmanager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactmanager.database.ContactsDatabaseHelper
import com.example.contactmanager.databinding.ActivityContactDetailBinding
import com.example.contactmanager.models.Contact

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding
    private lateinit var databaseHelper: ContactsDatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = ContactsDatabaseHelper(this)
        contactId = intent.getIntExtra("CONTACT_ID", -1)

        loadContactDetails()

        binding.buttonEditContact.setOnClickListener {
            val updatedContact = Contact(
                id = contactId,
                name = binding.editTextName.text.toString(),
                phone = binding.editTextPhone.text.toString(),
                email = binding.editTextEmail.text.toString()
            )
            databaseHelper.updateContact(updatedContact)
            setResult(RESULT_OK)
            finish() // Quay lại MainActivity
        }

        binding.buttonDeleteContact.setOnClickListener {
            databaseHelper.deleteContact(contactId)
            Toast.makeText(this, "Liên hệ đã bị xóa", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish() // Quay lại MainActivity
        }

        // Xử lý nút Call
        binding.buttonCallContact.setOnClickListener {
            val phoneUri = Uri.parse("tel:${binding.editTextPhone.text}")
            val callIntent = Intent(Intent.ACTION_CALL, phoneUri)
            try {
                startActivity(callIntent)
            } catch (e: SecurityException) {
                Toast.makeText(this, "Cần cấp quyền gọi điện", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút Email
        binding.buttonEmailContact.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${binding.editTextEmail.text}")
                putExtra(Intent.EXTRA_SUBJECT, "Liên hệ từ ContactManager")
            }
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent)
            } else {
                Toast.makeText(this, "Không có ứng dụng email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadContactDetails() {
        val contact = databaseHelper.getContactById(contactId)
        contact?.let {
            binding.editTextName.setText(it.name)
            binding.editTextPhone.setText(it.phone)
            binding.editTextEmail.setText(it.email)
        }
    }
}
