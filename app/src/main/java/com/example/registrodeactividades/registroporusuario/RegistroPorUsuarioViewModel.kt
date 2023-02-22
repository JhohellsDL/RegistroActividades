package com.example.registrodeactividades.registroporusuario

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contadorcasino.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import kotlinx.coroutines.*
import java.lang.reflect.Array.get

class RegistroPorUsuarioViewModel(
    private val dataBase: HijosDataBaseDao,
    private val userId: Long
): ViewModel() {

    private var _user = MutableLiveData<Hijo>()
    val user : LiveData<Hijo>
        get() = _user

    //-----------------------------------para coroutines------------------------------------------------
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
    private suspend fun getUserFromDataBase(): Hijo {
        return withContext(Dispatchers.IO){
            val userAux = dataBase.get(userId)
            val cad = stringUserLog(userAux)
            Log.i("hijo","userAux : $cad")
            userAux
        }
    }



//************ editar**********************************
    fun getUsuario() {
        uiScope.launch {
            var registro = get(userId)
            registro?.nombre = "tatorio"
            // Datos para actualizar
            Log.i("hijo", "${registro?.hijoId}")
            Log.i("hijo", "${registro}")
            update(registro!!)
            initializeUser()
        }
    }
    private suspend fun get(id: Long): Hijo? {
        return withContext(Dispatchers.IO){
            val userAux = dataBase.get(id)
            userAux
        }
    }
    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO) {
            dataBase.update(registro)
        }
    }
//*****************************************************

    private fun stringUserLog(userAux: Hijo): String {
        val cad = "ID: ${userAux.hijoId}\n" +
                "NOMBRE: ${userAux.nombre}\n" +
                "FOTO: ${userAux.photoResourceId}\n" +
                "FECHA: ${userAux.fecha}\n" +
                "PTS PREMIO: ${userAux.puntosPremio}\n" +
                "PTS CASTIGO: ${userAux.puntosCastigo}\n" +
                "PTS JUEGO: ${userAux.puntosJuego}\n" +
                "PTS AYER: ${userAux.puntosAyer}\n" +
                "PTS HOY: ${userAux.puntosHoy}\n" +
                "DINERO: ${userAux.dinero}\n"
        return cad
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
    }

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

    private suspend fun get(id: Long): Hijo {
        return withContext(Dispatchers.IO) {
            val pHijo = dataBase.get(id)
            pHijo
        }
    }*/
}