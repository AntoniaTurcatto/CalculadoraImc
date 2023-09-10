package com.nnt.calculadoraimc_turmac.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Calculo::class], version = 1)
@TypeConverters(ConversorDateLong::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun calculoDao():CalculoDao
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "calculadora_imc_tmb"
                    ).build()
                }
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }

        }
    }
}