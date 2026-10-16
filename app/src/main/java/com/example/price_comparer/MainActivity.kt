package com.example.price_comparer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("MainActivity created")

        findViewById<Button>(R.id.viewProductsButton).setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }

        findViewById<Button>(R.id.addProductButton).setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }

        findViewById<Button>(R.id.themeButton).setOnClickListener {
            showThemeDialog()
        }
    }

    private fun showThemeDialog() {
        val labels = arrayOf("Light", "Dark", "Follow system")
        val modes = intArrayOf(
            AppCompatDelegate.MODE_NIGHT_NO,
            AppCompatDelegate.MODE_NIGHT_YES,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        val selected = modes.indexOf(ThemeSettings.current(this)).coerceAtLeast(0)

        AlertDialog.Builder(this)
            .setTitle("Theme")
            .setSingleChoiceItems(labels, selected) { dialog, which ->
                Timber.i("Theme changed to ${labels[which]}")
                ThemeSettings.save(this, modes[which])
                dialog.dismiss()
            }
            .show()
    }
}