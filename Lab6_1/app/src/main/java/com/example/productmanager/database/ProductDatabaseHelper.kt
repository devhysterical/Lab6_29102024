package com.example.productmanager.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.productmanager.models.Product

class ProductDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Products.db"
        const val DATABASE_VERSION = 1

        const val TABLE_PRODUCTS = "Products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PRICE = "price"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PRICE REAL,
                $COLUMN_DESCRIPTION TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun insertProduct(product: Product): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_DESCRIPTION, product.description)
        }
        return db.insert(TABLE_PRODUCTS, null, values)
    }

    fun getAllProducts(): List<Product> {
        val db = readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null)
        val products = mutableListOf<Product>()
        while (cursor.moveToNext()) {
            val product = Product(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            )
            products.add(product)
        }
        cursor.close()
        return products
    }

    fun updateProduct(product: Product): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_DESCRIPTION, product.description)
        }
        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(product.id.toString()))
    }

    fun deleteProduct(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_PRODUCTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}