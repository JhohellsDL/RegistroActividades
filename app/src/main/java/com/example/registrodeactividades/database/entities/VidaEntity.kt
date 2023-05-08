package com.example.registrodeactividades.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vidas_table")
data class VidaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "foto") val foto: Int,
    @ColumnInfo(name = "fecha") val fecha: String,
    @ColumnInfo(name = "vidas") val vidas: Int
)