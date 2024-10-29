package com.example.notesmanager.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notesmanager.models.Note

class NotesDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "notes.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Notes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_DATE TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertNote(note: Note): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_DATE, note.date)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Notes", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val content = cursor.getString(cursor.getColumnIndexOrThrow("content"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                notes.add(Note(id, title, content, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notes
    }


    fun updateNote(note: Note): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_DATE, note.date)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(note.id.toString()))
    }

    fun deleteNoteById(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}
