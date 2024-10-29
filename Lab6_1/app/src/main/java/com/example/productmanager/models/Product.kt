package com.example.productmanager.models

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val description: String
)