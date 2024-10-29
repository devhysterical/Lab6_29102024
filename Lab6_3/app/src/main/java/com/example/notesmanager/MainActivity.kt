package com.example.notesmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesmanager.adapters.NoteAdapter
import com.example.notesmanager.database.NotesDatabaseHelper
import com.example.notesmanager.databinding.ActivityMainBinding
import com.example.notesmanager.models.Note

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: NotesDatabaseHelper
    private lateinit var notes: MutableList<Note>
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = NotesDatabaseHelper(this)

        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        // Tải danh sách ghi chú
        loadNotes()

        binding.buttonAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Tải lại danh sách ghi chú sau khi trở về từ màn hình AddNoteActivity
        loadNotes()
    }

    private fun loadNotes() {
        notes = databaseHelper.getAllNotes().toMutableList()
        adapter = NoteAdapter(this, notes)
        binding.recyclerViewNotes.adapter = adapter
    }
}