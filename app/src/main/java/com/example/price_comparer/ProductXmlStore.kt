package com.example.price_comparer

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlSerializer
import timber.log.Timber
import java.io.File

class ProductXmlStore(private val file: File) {

    fun save(products: List<Product>) {
        try {
            file.outputStream().use { out ->
                val s = Xml.newSerializer()
                s.setOutput(out, "UTF-8")
                s.startDocument("UTF-8", true)
                s.startTag(null, "products")
                for (p in products) {
                    s.startTag(null, "product")
                    s.attribute(null, "id", p.id.toString())
                    textTag(s, "title", p.title)
                    textTag(s, "description", p.description)
                    textTag(s, "price", p.price.toString())
                    s.endTag(null, "product")
                }
                s.endTag(null, "products")
                s.endDocument()
            }
            Timber.d("Saved ${products.size} products to ${file.name}")
        } catch (e: Exception) {
            Timber.e(e, "Failed to save products")
        }
    }

    fun load(): List<Product> {
        if (!file.exists()) {
            Timber.d("No saved products file yet")
            return emptyList()
        }

        val result = mutableListOf<Product>()
        try {
            file.inputStream().use { input ->
                val parser = Xml.newPullParser()
                parser.setInput(input, "UTF-8")
                var current: Product? = null
                var event = parser.eventType

                while (event != XmlPullParser.END_DOCUMENT) {
                    when (event) {
                        XmlPullParser.START_TAG -> when (parser.name) {
                            "product" -> current =
                                Product(id = parser.getAttributeValue(null, "id").toLong())
                            "title" -> current?.title = parser.nextText()
                            "description" -> current?.description = parser.nextText()
                            "price" -> current?.price = parser.nextText().toDouble()
                        }
                        XmlPullParser.END_TAG ->
                            if (parser.name == "product") {
                                current?.let { result.add(it) }
                                current = null
                            }
                    }
                    event = parser.next()
                }
            }
            Timber.d("Loaded ${result.size} products from ${file.name}")
            return result
        } catch (e: Exception) {
            Timber.e(e, "Failed to read products file, starting empty")
            return emptyList()
        }
    }

    private fun textTag(s: XmlSerializer, name: String, value: String) {
        s.startTag(null, name)
        s.text(value)
        s.endTag(null, name)
    }
}