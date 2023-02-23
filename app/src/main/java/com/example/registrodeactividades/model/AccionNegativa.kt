package com.example.registrodeactividades.model

data class AccionNegativa(
    val stringResourceId: Int,
    val imageResource: Int,
    val valor: Int,
    var contador: Int
) {
}