package com.example.price_comparer

import android.app.Application
import timber.log.Timber

class PriceComparerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }
        ThemeSettings.applySaved(this)
        AppData.init(this)
    }
}