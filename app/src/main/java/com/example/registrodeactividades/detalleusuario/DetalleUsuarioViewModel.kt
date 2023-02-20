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
import java.util.*

class DetalleUsuarioViewModel(
    val database: HijosDataBaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var _hijo = MutableLiveData<Hijo>()
    val hijo: LiveData<Hijo>
        get() = _hijo

    private var _hMatthew = MutableLiveData<Hijo>()
    val hMatthew: LiveData<Hijo>
        get() = _hMatthew

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

    val hijoAndrew = Hijo(
        //hijoId = 123456,
        photoResourceId = 0,
        nombre = "Andrew",
        fecha = Date().toString(),
        puntosPremio = 100,
        puntosCastigo = 20,
        puntosJuego = 500,
        puntosAyer = 300,
        puntosHoy = 200,
        dinero = 15.5f
    )
    val hijoMatthew = Hijo(
        //hijoId =123457,
        photoResourceId = 0,
        nombre = "Matthew",
        fecha = Date().toString(),
        puntosPremio = 100,
        puntosCastigo = 20,
        puntosJuego = 500,
        puntosAyer = 300,
        puntosHoy = 200,
        dinero = 15.5f
    )
    private suspend fun getHijoFromDataBase(): Hijo? {
        return withContext(Dispatchers.IO) {
            var phijo = database.getHijoUser()
            phijo
        }
    }
    fun onUnicoRegisterHijos() {
        uiScope.launch {
            insert(hijoAndrew)
            insert(hijoMatthew)
            _hijo.value = getHijoFromDataBase()
            val idAndrew = _hijo.value?.hijoId?.minus(1)
            _hMatthew.value = get(idAndrew!!)
            Log.i("hijo", "${_hijo.value?.hijoId}")
            Log.i("hijo", "${_hMatthew.value?.hijoId}")
        }
    }

    private suspend fun insert(newRegistro: Hijo) {
        withContext(Dispatchers.IO) {
            database.insert(newRegistro)
        }
    }

    fun getUsuario() {
        uiScope.launch {
            var registro = get(123487L)
            registro?.nombre = "tatorio"
            // Datos para actualizar
            Log.i("hijo", "${registro?.hijoId}")
            Log.i("hijo", "${registro}")
            update(registro!!)
        }
    }

    private suspend fun get(id: Long): Hijo? {
        return withContext(Dispatchers.IO) {
            var phijo = database.get(id)
            phijo
        }
    }

    fun onRegisterActividades() {
        uiScope.launch {
            var registro = database.get(29L)
            registro.nombre = "tatorio"
            // Datos para actualizar
            update(registro)
        }
    }

    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO) {
            database.update(registro)
        }
    }

    fun onLimpiarResgistro() {
        uiScope.launch {
            limpiarRegistro()
            _hijo.value = Hijo()
            Log.i("hijo", "Limpiando...")
        }
    }

    private suspend fun limpiarRegistro() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        Log.i("viewmodel", "Cerradasdfadfo!")
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToSleepDataQuality = MutableLiveData<Long>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long){
        _navigateToSleepDataQuality.value = id
    }

    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }
}