package com.example.price_comparer

import timber.log.Timber
import java.util.concurrent.atomic.AtomicLong

class ShoppingList(private val store: ProductXmlStore? = null) {
    private val products = ArrayList<Product>()
    private val lastId = AtomicLong(0L)

    init {
        store?.let {
            val loaded = it.load()
            products.addAll(loaded)
            lastId.set(loaded.maxOfOrNull { p -> p.id } ?: 0L)
        }
    }

    fun findAll(): List<Product> {
        return products
    }

    fun create(product: Product) {
        product.id = lastId.incrementAndGet()
        products.add(product)
        Timber.i("Created product id=${product.id}, title=${product.title}")
        save()
    }

    fun update(placemark: Product): Boolean {
        val foundProduct = findOne(placemark.id)
        return if (foundProduct != null) {
            foundProduct.title = placemark.title
            foundProduct.description = placemark.description
            foundProduct.price = placemark.price
            Timber.i("Updated product id=${placemark.id}")
            save()
            true
        } else {
            Timber.w("Update failed: id=${placemark.id} not found")
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundProduct = findOne(id)
        return if (foundProduct != null) {
            products.remove(foundProduct)
            Timber.i("Deleted product id=$id")
            save()
            true
        } else {
            Timber.w("Delete failed: id=$id not found")
            false
        }
    }

    fun findOne(id: Long): Product? {
        return products.find { p -> p.id == id }
    }

    private fun save() {
        store?.save(products)
    }
}