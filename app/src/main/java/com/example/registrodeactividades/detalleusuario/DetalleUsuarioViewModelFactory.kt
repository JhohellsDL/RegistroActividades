package com.example.registrodeactividades.detalleusuario

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contadorcasino.database.HijosDataBaseDao

class DetalleUsuarioViewModelFactory(
    private val dataSource: HijosDataBaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleUsuarioViewModel::class.java)) {
            return DetalleUsuarioViewModel(dataSource, application) as T
        }
        throw java.lang.IllegalArgumentException("View Model class Desconocida!")
    }
}