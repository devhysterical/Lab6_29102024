package com.example.notesmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesmanager.database.NotesDatabaseHelper
import com.example.notesmanager.databinding.ActivityEditNoteBinding
import com.example.notesmanager.models.Note

class EditNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var databaseHelper: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("NOTE_ID", -1)
        val note = databaseHelper.getNoteById(noteId)
        if (note != null) {
            binding.editTextTitle.setText(note.title)
            binding.editTextContent.setText(note.content)
        }

        binding.buttonSave.setOnClickListener {
            val updatedNote = Note(
                id = noteId,
                title = binding.editTextTitle.text.toString(),
                content = binding.editTextContent.text.toString(),
                date = note?.date ?: ""
            )
            databaseHelper.updateNote(updatedNote)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
