package com.example.registrodeactividades.registroporusuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registrodeactividades.providers.UserProvider

class UserDetailViewModelFactory(
    private val userProvider: UserProvider,
    private val userId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(userProvider, userId) as T
        }
        throw java.lang.IllegalArgumentException("View Model class desconocido!")
    }
}