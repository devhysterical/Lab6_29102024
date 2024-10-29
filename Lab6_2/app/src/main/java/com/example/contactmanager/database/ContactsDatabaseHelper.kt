package com.example.contactmanager.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contactmanager.models.Contact

class ContactsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CONTACTS = "Contacts"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_CONTACTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_EMAIL TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun insertContact(contact: Contact): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        return db.insert(TABLE_CONTACTS, null, values)
    }

    fun getAllContacts(): List<Contact> {
        val db = readableDatabase
        val cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null)
        val contacts = mutableListOf<Contact>()

        while (cursor.moveToNext()) {
            val contact = Contact(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            )
            contacts.add(contact)
        }
        cursor.close()
        return contacts
    }

    fun getContactById(id: Int): Contact? {
        val db = readableDatabase
        val cursor = db.query(
            "Contacts", null, "id = ?", arrayOf(id.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            Contact(id, name, phone, email)
        } else {
            null
        }.also {
            cursor.close()
            db.close()
        }
    }

    fun deleteContact(contactId: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_CONTACTS, "$COLUMN_ID=?", arrayOf(contactId.toString()))
    }

    fun updateContact(contact: Contact): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, contact.name)
            put(COLUMN_PHONE, contact.phone)
            put(COLUMN_EMAIL, contact.email)
        }
        return db.update(TABLE_CONTACTS, values, "$COLUMN_ID=?", arrayOf(contact.id.toString()))
    }
}