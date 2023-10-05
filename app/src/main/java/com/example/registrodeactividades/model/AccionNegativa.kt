package com.example.registrodeactividades.model

data class AccionNegativa(
    val id: Int,
    val stringResourceId: Int,
    val imageResource: Int,
    val valor: Int,
    var contador: Int
) {
}