package com.example.registrodeactividades.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accion_positiva_matthew")
data class AccionPositivaMatthew(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val stringIdUser: String,
    val stringResourceId: Int,
    val imageResource: Int,
    val valor: Int,
    var contador: Int
)