package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrodeactividades.model.AccionNegativa

@Dao
interface AccionNegativaDao {
    @Insert
    suspend fun insert(accionNegativa: AccionNegativa)

    @Query("SELECT * FROM accion_negativa")
    suspend fun getAll(): List<AccionNegativa>

    @Query("SELECT * FROM accion_negativa WHERE id = :id")
    suspend fun getById(id: Int): AccionNegativa?

    @Update
    suspend fun update(accionNegativa: AccionNegativa)

    @Delete
    suspend fun delete(accionNegativa: AccionNegativa)

}