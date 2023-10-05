package com.example.registrodeactividades.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accion_positiva")
data class AccionPositiva(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val stringResourceId: Int,
    val imageResource: Int,
    val valor: Int,
    var contador: Int
)