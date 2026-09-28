package com.example.price_comparer

import java.util.concurrent.atomic.AtomicLong

class ShoppingList{
    private val products = ArrayList<Product>()
    private val lastId = AtomicLong(0L)

    fun findAll(): List<Product> {
        return products
    }

    fun create(product: Product) {
        product.id = lastId.incrementAndGet()
        products.add(product)
    }

    fun update(placemark: Product): Boolean {
        val foundProduct = findOne(placemark.id)
        return if (foundProduct != null) {
            foundProduct.title = placemark.title
            foundProduct.description = placemark.description
            true
        } else {
            false
        }
    }

    fun delete(id: Long): Boolean {
        val foundPlacemark = findOne(id)
        return if (foundPlacemark != null) {
            products.remove(foundPlacemark)
            true
        } else {
            false
        }
    }

    fun findOne(id: Long): Product? {
        return products.find { p -> p.id == id }
    }
}