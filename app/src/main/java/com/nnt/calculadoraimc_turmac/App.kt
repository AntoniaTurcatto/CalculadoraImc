package com.nnt.calculadoraimc_turmac

import android.app.Application
import com.nnt.calculadoraimc_turmac.model.AppDatabase

class App: Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db=AppDatabase.getDatabase(this)
    }

}