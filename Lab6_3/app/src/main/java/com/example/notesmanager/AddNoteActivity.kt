package com.example.notesmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesmanager.database.NotesDatabaseHelper
import com.example.notesmanager.databinding.ActivityAddNoteBinding
import com.example.notesmanager.models.Note
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var databaseHelper: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = NotesDatabaseHelper(this)

        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val date = getCurrentDate()
                val newNote = Note(title = title, content = content, date = date)
                databaseHelper.insertNote(newNote)
                Toast.makeText(this, "Ghi chú đã được thêm!", Toast.LENGTH_SHORT).show()
                finish()  // Đóng Activity và quay về màn hình chính
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}
