package com.example.notesmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmanager.R
import com.example.notesmanager.models.Note

class NoteAdapter(private val context: Context, private val notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewTitle)
        val content: TextView = view.findViewById(R.id.textViewContent)
        val date: TextView = view.findViewById(R.id.textViewDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.content.text = note.content
        holder.date.text = note.date
    }

    override fun getItemCount(): Int = notes.size
}