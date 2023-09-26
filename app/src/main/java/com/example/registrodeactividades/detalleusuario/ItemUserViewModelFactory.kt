package com.example.registrodeactividades.detalleusuario

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registrodeactividades.providers.UserProvider

class ItemUserViewModelFactory(
    private val userProvider: UserProvider,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemUserViewModel::class.java)) {
            return ItemUserViewModel(userProvider, application) as T
        }
        throw java.lang.IllegalArgumentException("View Model class Desconocida!")
    }
}