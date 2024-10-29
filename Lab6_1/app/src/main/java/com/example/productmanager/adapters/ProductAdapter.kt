package com.example.productmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productmanager.R
import com.example.productmanager.models.Product

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.textViewProductName)
        val productPrice: TextView = itemView.findViewById(R.id.textViewProductPrice)
        val productDescription: TextView = itemView.findViewById(R.id.textViewProductDescription)

        init {
            itemView.setOnClickListener {
                onItemClick(products[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productName.text = product.name
        holder.productPrice.text = "Price: ${product.price}"
        holder.productDescription.text = product.description
    }

    override fun getItemCount() = products.size
}