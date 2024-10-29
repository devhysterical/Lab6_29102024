package com.example.contactmanager

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contactmanager.database.ContactsDatabaseHelper
import com.example.contactmanager.databinding.ActivityAddContactBinding
import com.example.contactmanager.models.Contact

class AddContactActivity : AppCompatActivity() {

    private lateinit var databaseHelper: ContactsDatabaseHelper
    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = ContactsDatabaseHelper(this)

        binding.buttonSaveContact.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val email = binding.editTextEmail.text.toString()

            val contact = Contact(name = name, phone = phone, email = email)
            databaseHelper.insertContact(contact)

            // Trả về kết quả thành công cho MainActivity
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
