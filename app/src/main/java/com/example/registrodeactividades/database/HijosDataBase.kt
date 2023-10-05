package com.example.registrodeactividades.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.registrodeactividades.database.dao.AccionNegativaDao
import com.example.registrodeactividades.database.dao.AccionNegativaMatthewDao
import com.example.registrodeactividades.database.dao.AccionPositivaDao
import com.example.registrodeactividades.database.dao.AccionPositivaMatthewDao
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionNegativaMatthew
import com.example.registrodeactividades.model.AccionPositiva
import com.example.registrodeactividades.model.AccionPositivaMatthew

@Database(entities = [AccionPositiva::class, AccionNegativa::class, AccionPositivaMatthew::class, AccionNegativaMatthew::class], version = 1, exportSchema = false)
abstract class HijosDataBase : RoomDatabase() {

    abstract val accionPositivaDao: AccionPositivaDao
    abstract val accionNegativaDao: AccionNegativaDao
    abstract val accionNegativaMatthewDao: AccionNegativaMatthewDao
    abstract val accionPositivaMatthewDao: AccionPositivaMatthewDao

    companion object{

        @Volatile
        private var INSTANCE: HijosDataBase? = null

        fun getInstance(context: Context) : HijosDataBase {
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