package com.example.price_comparer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import kotlin.random.Random

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var priceInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        priceInput = findViewById(R.id.priceInput)

        findViewById<Button>(R.id.saveButton).setOnClickListener { saveProduct() }
        findViewById<Button>(R.id.cancelButton).setOnClickListener { finish() }

        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }

        editingId?.let {
            Timber.d("Editing product id=$it")
            findViewById<TextView>(R.id.formTitle).text = "Edit Product"
            loadExistingProduct(it)
        }
    }

    private fun loadExistingProduct(id: Long) {
        val product = AppData.products.findOne(id)

        if (product == null) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        titleInput.setText(product.title)
        descriptionInput.setText(product.description)
        priceInput.setText(product.price.toString())
    }

    private fun saveProduct() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        var price = priceInput.text.toString().toDoubleOrNull()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            Timber.w("Save rejected: empty title")
            return
        }
        if (price == null) {
            price = (Random.nextDouble(1.0, 10.0) * 100).toInt() / 100.0
        }

        val id = editingId
        if (id == null) {
            AppData.products.create(Product(title = title, description = description, price = price))
            Timber.i("Product created, title=$title")
            Toast.makeText(this, "Product created", Toast.LENGTH_SHORT).show()
        } else {
            AppData.products.update(Product(id = id, title = title, description = description, price = price))
            Timber.i("Product updated id=$id")
            Toast.makeText(this, "Product updated", Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}