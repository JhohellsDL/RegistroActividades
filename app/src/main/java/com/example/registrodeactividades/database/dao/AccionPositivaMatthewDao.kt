package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.registrodeactividades.model.AccionPositivaMatthew

@Dao
interface AccionPositivaMatthewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accionPositiva: AccionPositivaMatthew)

    @Query("SELECT * FROM accion_positiva_matthew")
    fun getAll(): List<AccionPositivaMatthew>

    @Query("SELECT * FROM accion_positiva_matthew WHERE id = :id")
    fun getById(id: Int): AccionPositivaMatthew?

    @Update
    fun update(accionPositiva: AccionPositivaMatthew)

    @Delete
    fun delete(accionPositiva: AccionPositivaMatthew)

    @Query("DELETE FROM accion_positiva_matthew")
    fun deleteAll()
}