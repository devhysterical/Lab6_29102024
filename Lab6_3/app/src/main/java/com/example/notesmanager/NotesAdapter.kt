package com.example.notesmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmanager.databinding.ItemNoteBinding
import com.example.notesmanager.models.Note

class NotesAdapter(private val notes: List<Note>, private val onClick: (Note) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.textViewTitle.text = note.title
        holder.binding.textViewDate.text = note.date
        holder.binding.textViewContent.text = note.content
        holder.itemView.setOnClickListener { onClick(note) }
    }

    override fun getItemCount(): Int = notes.size
}
