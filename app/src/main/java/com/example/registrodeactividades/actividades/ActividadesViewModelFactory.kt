package com.example.registrodeactividades.actividades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModel

class ActividadesViewModelFactory(
    private val userId: Long,
    private val dataBaseDao: HijosDataBaseDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActividadesViewModel::class.java)) {
            return ActividadesViewModel(dataBaseDao, userId) as T
        }
        throw java.lang.IllegalArgumentException("View Model class Desconocida!")
    }
}