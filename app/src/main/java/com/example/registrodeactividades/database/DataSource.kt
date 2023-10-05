package com.example.registrodeactividades.database

import com.example.registrodeactividades.R
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionPositiva

class DataSource {
    fun loadPositiveActions(): List<AccionPositiva> {
        return listOf(
            AccionPositiva(
                id = 1,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction1,
                imageResource = R.drawable.platos_limpios,
                valor = 35,
                contador = 0
            ),
            AccionPositiva(
                id = 2,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction2,
                imageResource = R.drawable.arr_sala,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                id = 3,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction3,
                imageResource = R.drawable.arr_cocina,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                id = 4,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction4,
                imageResource = R.drawable.arr_ropa,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                id = 5,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction5,
                imageResource = R.drawable.trapear,
                valor = 30,
                contador = 0
            ),
            AccionPositiva(
                id = 6,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction6,
                imageResource = R.drawable.lav_cocina,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                id = 7,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction7,
                imageResource = R.drawable.ser_amable,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                id = 8,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction9,
                imageResource = R.drawable.cuidar,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                id = 9,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction10,
                imageResource = R.drawable.rec_juguetes,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                id = 10,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction11,
                imageResource = R.drawable.lav_ba_o,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                id = 11,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction12,
                imageResource = R.drawable.ayudar,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                id = 12,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction13,
                imageResource = R.drawable.cocinar,
                valor = 20,
                contador = 0
            ),
            AccionPositiva(
                id = 13,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction14,
                imageResource = R.drawable.jug_riley,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                id = 14,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction15,
                imageResource = R.drawable.lav_pies,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                id = 15,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction16,
                imageResource = R.drawable.lleg_temprano,
                valor = 5,
                contador = 0
            ),
            AccionPositiva(
                id = 16,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction17,
                imageResource = R.drawable.hablar_ingles,
                valor = 10,
                contador = 0
            ),
            AccionPositiva(
                id = 17,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction18,
                imageResource = R.drawable.groseria,
                valor = 25,
                contador = 0
            ),
            AccionPositiva(
                id = 18,
                stringIdUser = "",
                stringResourceId = R.string.positiveAction19,
                imageResource = R.drawable.no_escuchar,
                valor = 50,
                contador = 0
            )
        )
    }
    fun loadNegativeActions(): List<AccionNegativa> {
        return listOf(
            AccionNegativa(
                id = 1,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction1,
                imageResource = R.drawable.cara_fea,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 2,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction2,
                imageResource = R.drawable.ser_malo,
                valor = 20,
                contador = 0
            ),
            AccionNegativa(
                id = 3,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction3,
                imageResource = R.drawable.molestar,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 4,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction4,
                imageResource = R.drawable.golpear,
                valor = 30,
                contador = 0
            ),
            AccionNegativa(
                id = 5,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction5,
                imageResource = R.drawable.adios,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 6,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction6,
                imageResource = R.drawable.comportamiento,
                valor = 30,
                contador = 0
            ),
            AccionNegativa(
                id = 7,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction7,
                imageResource = R.drawable.problema,
                valor = 30,
                contador = 0
            ),
            AccionNegativa(
                id = 8,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction9,
                imageResource = R.drawable.sucio__1_,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 9,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction10,
                imageResource = R.drawable.mentiroso,
                valor = 20,
                contador = 0
            ),
            AccionNegativa(
                id = 10,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction11,
                imageResource = R.drawable.incomodo,
                valor = 20,
                contador = 0
            ),
            AccionNegativa(
                id = 11,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction12,
                imageResource = R.drawable.incomodo__1_,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 12,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction13,
                imageResource = R.drawable.jugador,
                valor = 20,
                contador = 0
            ),
            AccionNegativa(
                id = 13,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction14,
                imageResource = R.drawable.abuso_verbal,
                valor = 15,
                contador = 0
            ),
            AccionNegativa(
                id = 14,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction15,
                imageResource = R.drawable.reflexologia,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 15,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction16,
                imageResource = R.drawable.tarde,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 16,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction17,
                imageResource = R.drawable.no_hablar_ingles,
                valor = 10,
                contador = 0
            ),
            AccionNegativa(
                id = 17,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction18,
                imageResource = R.drawable.no_escuchar,
                valor = 50,
                contador = 0
            ),
            AccionNegativa(
                id = 18,
                stringIdUser = "",
                stringResourceId = R.string.negativeAction19,
                imageResource = R.drawable.silencio,
                valor = 50,
                contador = 0
            )
        )
    }
}