package com.example.productmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productmanager.adapters.ProductAdapter
import com.example.productmanager.database.ProductDatabaseHelper
import com.example.productmanager.models.Product
import com.example.productmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: ProductDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = ProductDatabaseHelper(this)

        // Set up RecyclerView
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        loadProducts()

        // Handle Add Product button click
        binding.buttonAddProduct.setOnClickListener {
            addProduct()
        }
    }

    private fun addProduct() {
        val name = binding.editTextProductName.text.toString().trim()
        val price = binding.editTextProductPrice.text.toString().toDoubleOrNull()
        val description = binding.editTextProductDescription.text.toString().trim()

        if (name.isEmpty() || price == null) {
            Toast.makeText(this, "Please enter valid product details", Toast.LENGTH_SHORT).show()
            return
        }

        val product = Product(name = name, price = price, description = description)
        databaseHelper.insertProduct(product)
        loadProducts()

        // Clear input fields
        binding.editTextProductName.text.clear()
        binding.editTextProductPrice.text.clear()
        binding.editTextProductDescription.text.clear()

        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
    }

    private fun loadProducts() {
        val products = databaseHelper.getAllProducts()
        binding.recyclerViewProducts.adapter = ProductAdapter(products) { product ->
            // Handle product click here (e.g., edit or delete product)
        }
    }
}