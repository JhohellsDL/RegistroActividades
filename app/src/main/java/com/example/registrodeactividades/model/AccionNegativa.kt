package com.example.registrodeactividades.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accion_negativa")
data class AccionNegativa(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val stringResourceId: Int,
    val imageResource: Int,
    val valor: Int,
    var contador: Int
)