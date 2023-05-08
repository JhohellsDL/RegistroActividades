package com.example.registrodeactividades.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.registrodeactividades.database.entities.VidaEntity

@Dao
interface VidasDao {

    @Query("SELECT * FROM vidas_table")
    suspend fun getAllLista(): List<VidaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lista: List<VidaEntity>)
}