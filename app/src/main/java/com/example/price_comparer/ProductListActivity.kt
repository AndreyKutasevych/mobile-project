package com.example.price_comparer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductListActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        adapter = ProductAdapter(
            products = AppData.products.findAll(),
            onEdit = { product -> editProduct(product) },
            onDelete = { product -> deleteProduct(product) }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.productsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.returnButton).setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateProducts(AppData.products.findAll())
    }

    private fun editProduct(product: Product) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", product.id)
        startActivity(intent)
    }

    private fun deleteProduct(product: Product) {
        AppData.products.delete(product.id)
        adapter.updateProducts(AppData.products.findAll())
    }
}