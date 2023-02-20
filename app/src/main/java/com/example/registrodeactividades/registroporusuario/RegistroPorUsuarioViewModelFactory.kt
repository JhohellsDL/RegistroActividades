package com.example.registrodeactividades.registroporusuario

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModel
import javax.sql.DataSource

class RegistroPorUsuarioViewModelFactory(
    private val dataSource: HijosDataBaseDao,
    private val userId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroPorUsuarioViewModel::class.java)) {
            return RegistroPorUsuarioViewModel(dataSource, userId) as T
        }
        throw java.lang.IllegalArgumentException("View Model class Desconocida!")
    }
}