package com.example.registrodeactividades.detalleusuario

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contadorcasino.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.R
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
    }

    val hijoAndrew = Hijo(
        photoResourceId = R.drawable.andrew,
        nombre = "Andrew",
        fecha = Date().toString(),
        puntosPremio = 0,
        puntosCastigo = 0,
        puntosJuego = 0,
        puntosAyer = 0,
        puntosHoy = 0,
        dinero = 0.0f
    )
    val hijoMatthew = Hijo(
        photoResourceId = R.drawable.matthew,
        nombre = "Matthew",
        fecha = Date().toString(),
        puntosPremio = 0,
        puntosCastigo = 0,
        puntosJuego = 0,
        puntosAyer = 0,
        puntosHoy = 0,
        dinero = 0.0f
    )

    fun onReinicioRegistroHijos() {
        uiScope.launch {
            limpiarRegistro()
            _hijo.value = Hijo()
            insert(hijoAndrew)
            insert(hijoMatthew)
            val idNew = _hijo.value?.hijoId?.minus(1)
            _hMatthew.value = get(idNew!!)
        }
    }
    private suspend fun get(id: Long): Hijo {
        return withContext(Dispatchers.IO) {
            val phijo = database.get(id)
            phijo
        }
    }

    private suspend fun insert(newRegistro: Hijo) {
        withContext(Dispatchers.IO) {
            database.insert(newRegistro)
        }
    }
    private suspend fun limpiarRegistro() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private val _idUserForNavigation = MutableLiveData<Long?>()
    val idUserForNavigation
        get() = _idUserForNavigation

    fun onUserClicked(id: Long){
        _idUserForNavigation.value = id
    }

    fun onUserClickedNavigated() {
        _idUserForNavigation.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}