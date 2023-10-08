package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.registrodeactividades.model.AccionPositiva

@Dao
interface AccionPositivaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accionPositiva: AccionPositiva)

    @Query("SELECT * FROM accion_positiva")
    fun getAll(): List<AccionPositiva>

    @Update
    fun update(accionPositiva: AccionPositiva)

    @Delete
    fun delete(accionPositiva: AccionPositiva)

    @Query("DELETE FROM accion_positiva")
    fun deleteAll()
}