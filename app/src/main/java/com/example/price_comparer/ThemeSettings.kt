package com.example.price_comparer

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeSettings {
    private const val PREFS = "settings"
    private const val KEY_MODE = "night_mode"

    fun current(context: Context): Int =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_MODE, AppCompatDelegate.MODE_NIGHT_YES)

    fun applySaved(context: Context) {
        AppCompatDelegate.setDefaultNightMode(current(context))
    }

    fun save(context: Context, mode: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putInt(KEY_MODE, mode).apply()
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}