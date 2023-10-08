package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.registrodeactividades.model.AccionNegativa

@Dao
interface AccionNegativaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accionNegativa: AccionNegativa)

    @Query("SELECT * FROM accion_negativa")
    fun getAll(): List<AccionNegativa>

    @Update
    fun update(accionNegativa: AccionNegativa)

    @Delete
    fun delete(accionNegativa: AccionNegativa)

    @Query("DELETE FROM accion_negativa")
    fun deleteAll()
}