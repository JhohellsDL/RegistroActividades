package com.example.registrodeactividades.registroporusuario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrodeactividades.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.utils.Precios
import kotlinx.coroutines.*

class RegistroPorUsuarioViewModel(
    private val dataBase: HijosDataBaseDao,
    private val userId: Long
): ViewModel() {

    private var _user = MutableLiveData<Hijo>()
    val user: LiveData<Hijo>
        get() = _user

    private var _dineroGanado = MutableLiveData<Float>()
    val dineroGanado: LiveData<Float>
        get() = _dineroGanado

    private var _dineroPerdido = MutableLiveData<Float>()
    val dineroPerdido: LiveData<Float>
        get() = _dineroPerdido

    //-----------------------------------para coroutines------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //--------------------------------------------------------------------------------------------------

    init {
        initializeUser()

        /*_dineroGanado.value = 0.0f
        _dineroPerdido.value = 0.0f*/
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDataBase()
            _dineroPerdido.value = _user.value?.puntosCastigo!! * Precios.ACTIVIDAD_NEGATIVA.value
            _dineroGanado.value = _user.value?.puntosPremio!! * Precios.ACTIVIDAD_POSITIVA.value
        }
    }

    private suspend fun getUserFromDataBase(): Hijo {
        return withContext(Dispatchers.IO) {
            val userAux = dataBase.get(userId)
            userAux
        }
    }

    fun actualizarDatos() {
        initializeUser()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
/*
//************ editar**********************************
 */
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

    fun onRegisterActividades() {
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
    }
    */
 */
