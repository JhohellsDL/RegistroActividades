package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.registrodeactividades.model.AccionNegativaMatthew

@Dao
interface AccionNegativaMatthewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accionNegativa: AccionNegativaMatthew)

    @Query("SELECT * FROM accion_negativa")
    fun getAll(): List<AccionNegativaMatthew>

    @Query("SELECT * FROM accion_negativa WHERE id = :id")
    fun getById(id: Int): AccionNegativaMatthew?

    @Update
    fun update(accionNegativa: AccionNegativaMatthew)

    @Delete
    fun delete(accionNegativa: AccionNegativaMatthew)
}