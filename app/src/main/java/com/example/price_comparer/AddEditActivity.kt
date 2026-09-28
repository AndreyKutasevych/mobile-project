package com.example.price_comparer

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText

    private lateinit var priceInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createUserInterface()

        editingId = intent.getLongExtra("id", -1L)

        if (editingId != -1L) {
            loadExistingProduct(editingId!!)
        }
    }

    private fun createUserInterface() {

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        titleInput = EditText(this).apply {
            hint = "Title"
        }

        descriptionInput = EditText(this).apply {
            hint = "Description"
        }

        priceInput = EditText(this).apply {
            hint = "Price"
            inputType =
                android.text.InputType.TYPE_CLASS_NUMBER or
                        android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        val saveButton = Button(this).apply {
            text = "Save"

            setOnClickListener {
                saveProduct()
            }
        }

        val cancelButton = Button(this).apply {
            text = "Cancel"

            setOnClickListener {
                finish()
            }
        }

        root.addView(titleInput)
        root.addView(descriptionInput)
        root.addView(priceInput)
        root.addView(saveButton)
        root.addView(cancelButton)

        setContentView(root)
    }

    private fun loadExistingProduct(id: Long) {

        val product = AppData.products.findOne(id)

        if (product == null) {
            Toast.makeText(
                this,
                "Product not found",
                Toast.LENGTH_SHORT
            ).show()

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
            return
        }
        if (price == null) {
            price = (Random.nextDouble(1.0, 10.0) * 100).toInt() / 100.0
        }

        if (editingId == null || editingId == -1L) {

            val product = Product(
                title = title,
                description = description,
                price = price,
            )

            AppData.products.create(product)

            Toast.makeText(
                this,
                "Product created",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            val product = Product(
                id = editingId!!,
                title = title,
                description = description,
                price = price,
            )

            AppData.products.update(product)

            Toast.makeText(
                this,
                "Product updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}