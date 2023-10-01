package com.example.registrodeactividades.actividades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registrodeactividades.providers.UserProvider

class ActividadesViewModelFactory(
    private val userProvider: UserProvider,
    private val userId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActividadesViewModel::class.java)) {
            return ActividadesViewModel(userProvider, userId) as T
        }
        throw java.lang.IllegalArgumentException("View Model class Desconocida!")
    }
}