package com.example.price_comparer

import android.content.Context
import java.io.File

object AppData {
    lateinit var products: ShoppingList
        private set

    fun init(context: Context) {
        if (!::products.isInitialized) {
            products = ShoppingList(
                ProductXmlStore(File(context.filesDir, "products.xml"))
            )
        }
    }
}