package com.example.registrodeactividades.detalleusuario

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registrodeactividades.database.dao.AccionNegativaDao
import com.example.registrodeactividades.database.dao.AccionNegativaMatthewDao
import com.example.registrodeactividades.database.dao.AccionPositivaDao
import com.example.registrodeactividades.database.dao.AccionPositivaMatthewDao

class DetalleUsuarioViewModelFactory(
    private val dataActionPositiveSource: AccionPositivaDao,
    private val dataActionNegativeSource: AccionNegativaDao,
    private val dataActionPositivaMatthewSource: AccionPositivaMatthewDao,
    private val dataActionNegativaMatthewSource: AccionNegativaMatthewDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleUsuarioViewModel::class.java)) {
            return DetalleUsuarioViewModel(dataActionPositiveSource, dataActionNegativeSource, dataActionPositivaMatthewSource, dataActionNegativaMatthewSource, application) as T
        }
        throw java.lang.IllegalArgumentException("View Model class Desconocida!")
    }
}