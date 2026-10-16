package com.example.price_comparer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class ProductListActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        adapter = ProductAdapter(
            products = AppData.products.findAll(),
            listener = object : OnProductListener {
                override fun onProductClick(product: Product) {
                    editProduct(product)
                }

                override fun onProductLongClick(product: Product): Boolean {
                    confirmDelete(product)
                    return true
                }
            }
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
        Timber.d("Editing product id=${product.id}, title=${product.title}")
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", product.id)
        startActivity(intent)
    }

    private fun confirmDelete(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Delete this product?")
            .setMessage(product.title)
            .setPositiveButton("Delete") { _, _ -> deleteProduct(product) }
            .setNegativeButton("Cancel") { _, _ ->
                Timber.d("Delete cancelled id=${product.id}")
            }
            .show()
    }

    private fun deleteProduct(product: Product) {
        Timber.d("Deleting product id=${product.id}")
        AppData.products.delete(product.id)
        adapter.updateProducts(AppData.products.findAll())
    }
}