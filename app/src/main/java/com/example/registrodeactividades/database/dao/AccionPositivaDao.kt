package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrodeactividades.model.AccionPositiva

@Dao
interface AccionPositivaDao {
    @Insert
    suspend fun insert(accionPositiva: AccionPositiva)

    @Query("SELECT * FROM accion_positiva")
    suspend fun getAll(): List<AccionPositiva>

    @Query("SELECT * FROM accion_positiva WHERE id = :id")
    suspend fun getById(id: Int): AccionPositiva?

    @Update
    suspend fun update(accionPositiva: AccionPositiva)

    @Delete
    suspend fun delete(accionPositiva: AccionPositiva)
}