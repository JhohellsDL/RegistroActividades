package com.example.contadorcasino.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Hijo::class], version = 1, exportSchema = false)
abstract class HijosDataBase : RoomDatabase() {

    abstract val hijosDataBaseDao: HijosDataBaseDao

    companion object{

        @Volatile
        private var INSTANCE: HijosDataBase? = null

        fun getInstance(context: Context) : HijosDataBase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HijosDataBase::class.java,
                        "hijos_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}