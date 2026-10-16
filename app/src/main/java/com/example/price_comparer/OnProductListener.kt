package com.example.price_comparer

interface OnProductListener {
    fun onProductClick(product: Product)
    fun onProductLongClick(product: Product): Boolean
}