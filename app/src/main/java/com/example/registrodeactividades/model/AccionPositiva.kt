package com.example.registrodeactividades.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accion_positiva")
data class AccionPositiva(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val stringIdUser: String,
    val stringResourceId: Int,
    val imageResource: Int,
    val valor: Int,
    var contador: Int
) {
    fun toAccionPositivaMatthew(): AccionPositivaMatthew {
        return AccionPositivaMatthew(
            id = id,
            stringIdUser = stringIdUser,
            stringResourceId = stringResourceId,
            imageResource = imageResource,
            valor = valor,
            contador = contador
        )
    }
}