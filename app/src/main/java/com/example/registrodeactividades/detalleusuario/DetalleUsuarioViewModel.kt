package com.example.registrodeactividades.detalleusuario

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contadorcasino.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import kotlinx.coroutines.*

class DetalleUsuarioViewModel(
    val database: HijosDataBaseDao,
    application: Application
): AndroidViewModel(application) {

    private var _hijo = MutableLiveData<Hijo>()
    val hijo: LiveData<Hijo>
        get() = _hijo

    val hijos = database.getAllHijos()

    //-----------------------------------para coroutinas------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //--------------------------------------------------------------------------------------------------


    init {
        initializeHijo()
    }

    private fun initializeHijo() {
        uiScope.launch {
            _hijo.value = getHijoFromDataBase()
        }
    }

    val hijoAux1 = Hijo(nombre = "Andrew")
    val hijoAux2 = Hijo(nombre = "Matthew")
    private suspend fun getHijoFromDataBase(): Hijo? {
        return withContext(Dispatchers.IO){
            var phijo = database.getHijoUser()
            phijo
        }
    }

    fun onNewRegisterHijo(){
        uiScope.launch {
            val newRegistro = Hijo()
            insert(hijoAux1)
            _hijo.value = getHijoFromDataBase()
            Log.i("hijo","${_hijo.value?.hijoId}")
            Log.i("hijo","${hijos}")
        }
    }
    private suspend fun insert(newRegistro: Hijo) {
        withContext(Dispatchers.IO){
            database.insert(newRegistro)
        }
    }

    fun onRegisterActividades(){
        uiScope.launch {
            var registro = _hijo.value ?: return@launch
            // Datos para actualizar
            update(registro)
        }
    }
    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO){
            database.update(registro)
        }
    }

    fun onLimpiarResgistro(){
        uiScope.launch {
            limpiarRegistro()
            _hijo.value = Hijo()
            Log.i("hijo","Limpiando...")
        }
    }

    private suspend fun limpiarRegistro() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    override fun onCleared() {
        Log.i("viewmodel","Cerradasdfadfo!")
        super.onCleared()
        viewModelJob.cancel()
    }
}