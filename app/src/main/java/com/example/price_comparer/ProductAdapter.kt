package com.example.price_comparer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class ProductAdapter(
    private var products: List<Product>,
    private val listener: OnProductListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
        val priceText: TextView = itemView.findViewById(R.id.priceText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.titleText.text = "${product.id}: ${product.title}"
        holder.descriptionText.text = product.description
        holder.priceText.text = String.format("€%.2f", product.price)

        holder.itemView.setOnClickListener {
            Timber.d("Item tapped id=${product.id}")
            listener.onProductClick(product)
        }
        holder.itemView.setOnLongClickListener {
            Timber.d("Item long-pressed id=${product.id}")
            listener.onProductLongClick(product)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}