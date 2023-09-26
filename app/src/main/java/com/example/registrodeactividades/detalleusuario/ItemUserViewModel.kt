package com.example.registrodeactividades.detalleusuario

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.registrodeactividades.model.UserData
import com.example.registrodeactividades.model.mapToUserData
import com.example.registrodeactividades.providers.UserProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ItemUserViewModel(
    val userProvider: UserProvider,
    application: Application
) : AndroidViewModel(application) {

    private var _userAndrew = MutableLiveData<UserData>()
    val userAndrew: LiveData<UserData>
        get() = _userAndrew

    private var _userMatthew = MutableLiveData<UserData>()
    val userMatthew: LiveData<UserData>
        get() = _userMatthew

    private var _listUsers = MutableLiveData<List<UserData>>()
    val listUsers: LiveData<List<UserData>>
        get() = _listUsers

    private val listUsersAux = mutableListOf<UserData>()

    //-----------------------------------para coroutinas------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //--------------------------------------------------------------------------------------------------

    init {
        getUsers()
    }

    private fun onUser() {
        uiScope.launch {
            //getUser("2PDGhfqN0MyCYPzKNDiF")
        }
    }
    private fun getUserAndrew(){
        userProvider.getUserData("2PDGhfqN0MyCYPzKNDiF").addOnSuccessListener { it2 ->
            if (it2.exists()) {
                it2.data?.let {
                    _userAndrew.value = mapToUserData(it)
                }
            }
        }
    }

    private fun getUserMatthew(){
        userProvider.getUserData("k9PABZm7tdsr5c6ATtT1").addOnSuccessListener { it2 ->
            if (it2.exists()) {
                it2.data?.let {
                    _userMatthew.value = mapToUserData(it)
                }
            }
        }
    }

    private fun getUsers() {
        userProvider.getUserData("k9PABZm7tdsr5c6ATtT1").addOnSuccessListener { document ->
            if (document.exists()) {
                document.data?.let {
                    _userMatthew.value = mapToUserData(it)
                    listUsersAux.add(_userMatthew.value!!)
                    _listUsers.value = listUsersAux
                }
            }
        }
        userProvider.getUserData("2PDGhfqN0MyCYPzKNDiF").addOnSuccessListener { document ->
            if (document.exists()) {
                document.data?.let {
                    _userAndrew.value = mapToUserData(it)
                    listUsersAux.add(_userAndrew.value!!)
                    _listUsers.value = listUsersAux
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}