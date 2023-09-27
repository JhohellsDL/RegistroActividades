package com.example.registrodeactividades.registroporusuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrodeactividades.model.UserData
import com.example.registrodeactividades.model.mapToUserData
import com.example.registrodeactividades.providers.UserProvider

class UserDetailViewModel(
    private val userProvider: UserProvider,
    private val userId: String
): ViewModel() {

    private var _currentUser = MutableLiveData<UserData>()
    val currentUser: LiveData<UserData>
        get() = _currentUser

    init {
        getCurrentUser(userId)
    }
    private fun getCurrentUser(userId: String){
        userProvider.getUserData(userId).addOnSuccessListener { it2 ->
            if (it2.exists()) {
                it2.data?.let {
                    _currentUser.value = mapToUserData(it)
                }
            }
        }
    }

}