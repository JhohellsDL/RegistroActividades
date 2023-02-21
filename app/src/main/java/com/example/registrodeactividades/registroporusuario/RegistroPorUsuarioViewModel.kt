package com.example.registrodeactividades.registroporusuario

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contadorcasino.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import kotlinx.coroutines.*
import okhttp3.Dispatcher

class RegistroPorUsuarioViewModel(
    val dataBase: HijosDataBaseDao,
    private val userId: Long
): ViewModel() {

    private var _user = MutableLiveData<Hijo>()
    val user : LiveData<Hijo>
        get() = _user

    //-----------------------------------para coroutinas------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //--------------------------------------------------------------------------------------------------

    init {
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDataBase()
        }
    }
    private suspend fun getUserFromDataBase(): Hijo? {
        return withContext(Dispatchers.IO){
            val puser = dataBase.get(userId)
            Log.i("hijo","--puser ${puser.nombre}")
            puser
        }
    }

    /*fun onRegisterActividades() {
        uiScope.launch {
            Log.i("hijo","--registro $userId")
            val registro = dataBase.get(123497L)
            //registro.nombre = "tatorio"
            // Datos para actualizar
            Log.i("hijo","--registro ${registro.nombre}")
            update(registro)
        }
    }*/

    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO) {
            dataBase.update(registro)
            Log.i("hijo","-- ${registro.nombre}")
        }
    }

    fun getUsuario() {
        uiScope.launch {
            var registro = get(userId)
            registro?.nombre = "tatorio"
            // Datos para actualizar
            Log.i("hijo", "${registro?.hijoId}")
            Log.i("hijo", "${registro}")
            update(registro!!)
        }
    }

    private suspend fun get(id: Long): Hijo? {
        return withContext(Dispatchers.IO) {
            var phijo = dataBase.get(id)
            phijo
        }
    }
}