package com.example.registrodeactividades.database

import com.example.registrodeactividades.R
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionPositiva

class DataSource {
    fun loadPositiveActions(): List<AccionPositiva> {
        return listOf(
            AccionPositiva(
                stringResourceId = R.string.positiveAction1,
                imageResource = R.drawable.platos_limpios,
                valor = 35,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction2,
                imageResource = R.drawable.arr_sala,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction3,
                imageResource = R.drawable.arr_cocina,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction4,
                imageResource = R.drawable.arr_ropa,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction5,
                imageResource = R.drawable.trapear,
                valor = 30,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction6,
                imageResource = R.drawable.lav_cocina,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction7,
                imageResource = R.drawable.ser_amable,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction8,
                imageResource = R.drawable.ban_perro,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction9,
                imageResource = R.drawable.cuidar,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction10,
                imageResource = R.drawable.rec_juguetes,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction11,
                imageResource = R.drawable.lav_ba_o,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction12,
                imageResource = R.drawable.ayudar,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction13,
                imageResource = R.drawable.cocinar,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction14,
                imageResource = R.drawable.jug_riley,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction15,
                imageResource = R.drawable.lav_pies,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction16,
                imageResource = R.drawable.lleg_temprano,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                stringResourceId = R.string.positiveAction17,
                imageResource = R.drawable.hablar_ingles,
                valor = 10,
                contador = 0
            )
        )
    }
    fun loadNegativeActions(): List<AccionNegativa> {
        return listOf(
            AccionNegativa(
                stringResourceId = R.string.negativeAction1,
                imageResource = R.drawable.cara_fea,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction2,
                imageResource = R.drawable.ser_malo,
                valor = 20
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction3,
                imageResource = R.drawable.molestar,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction4,
                imageResource = R.drawable.golpear,
                valor = 30
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction5,
                imageResource = R.drawable.adios,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction6,
                imageResource = R.drawable.comportamiento,
                valor = 30
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction7,
                imageResource = R.drawable.problema,
                valor = 30
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction8,
                imageResource = R.drawable.enfermo,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction9,
                imageResource = R.drawable.sucio__1_,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction10,
                imageResource = R.drawable.mentiroso,
                valor = 20
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction11,
                imageResource = R.drawable.incomodo,
                valor = 20
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction12,
                imageResource = R.drawable.incomodo__1_,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction13,
                imageResource = R.drawable.jugador,
                valor = 20
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction14,
                imageResource = R.drawable.abuso_verbal,
                valor = 15
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction15,
                imageResource = R.drawable.reflexologia,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction16,
                imageResource = R.drawable.tarde,
                valor = 10
            ),
            AccionNegativa(
                stringResourceId = R.string.negativeAction17,
                imageResource = R.drawable.no_hablar_ingles,
                valor = 10
            )
        )
    }
}