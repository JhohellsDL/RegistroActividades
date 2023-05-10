package com.example.registrodeactividades.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hijos_table")
data class Hijo(
    @PrimaryKey(autoGenerate = true)
    var hijoId: Long = 0L,

    @ColumnInfo(name = "foto_hijo")
    var photoResourceId: Int = 0,

    @ColumnInfo(name = "nombre_hijo")
    var nombre: String = "hijo1",

    @ColumnInfo(name = "fecha")
    var fecha: String = "hijo1",

    @ColumnInfo(name = "fecha_actual")
    var fechaACtual: String = "hijoactual",

    @ColumnInfo(name = "puntos_premio")
    var puntosPremio: Int = 0,

    @ColumnInfo(name = "puntos_castigo")
    var puntosCastigo: Int = 0,

    @ColumnInfo(name = "puntos_juego")
    var puntosJuego: Int = 0,

    @ColumnInfo(name = "puntos_ayer")
    var puntosAyer: Int = 0,

    @ColumnInfo(name = "puntos_hoy")
    var puntosHoy: Int = 0,

    @ColumnInfo(name = "dinero")
    var dinero: Float = -1f,

    @ColumnInfo(name = "vidas")
    var vidas: Int = 21
)