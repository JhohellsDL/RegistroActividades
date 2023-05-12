package com.example.registrodeactividades.registroporusuario

import android.text.format.DateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrodeactividades.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.utils.Precios
import kotlinx.coroutines.*
import java.util.Date

class RegistroPorUsuarioViewModel(
    private val dataBase: HijosDataBaseDao,
    private val userId: Long
): ViewModel() {

    private var _user = MutableLiveData<Hijo>()
    val user: LiveData<Hijo>
        get() = _user

    private var _dineroTotal = MutableLiveData<Float>()
    val dineroTotal: LiveData<Float>
        get() = _dineroTotal

    private var _dineroGanado = MutableLiveData<Float>()
    val dineroGanado: LiveData<Float>
        get() = _dineroGanado

    private var _dineroPerdido = MutableLiveData<Float>()
    val dineroPerdido: LiveData<Float>
        get() = _dineroPerdido

    private var _vidas = MutableLiveData<Int>()
    val vidas: LiveData<Int>
        get() = _vidas

    private var _vidasAntes = MutableLiveData<Int>()
    val vidasAntes: LiveData<Int>
        get() = _vidasAntes

    private var _fecha = MutableLiveData<String>()
    val fecha: LiveData<String>
        get() = _fecha
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
            _dineroPerdido.value = _user.value?.puntosCastigo!! * Precios.ACTIVIDAD_NEGATIVA.value
            _dineroGanado.value = _user.value?.puntosPremio!! * Precios.ACTIVIDAD_POSITIVA.value
            _vidas.value = _user.value?.vidas
            _vidasAntes.value = _user.value?.vidas
            _fecha.value = formatGMTDate ()
            val cad = stringUserLog(user.value!!)
            Log.i("hijo", "USER INICIALIZADO : $cad")
        }
    }

    private suspend fun getUserFromDataBase(): Hijo {
        return withContext(Dispatchers.IO) {
            val userAux = dataBase.get(userId)
            userAux
        }
    }

    fun actualizarVidas(vidas: Int){
        registroDatos(vidas)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun menosVidas(){
        _vidas.value = _vidas.value?.minus(1)
    }

    fun masVidas(){
        _vidas.value = _vidas.value?.plus(1)
    }

    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO) {
            dataBase.update(registro)
        }
    }

    fun registroDatos(vidas: Int) {
        uiScope.launch {
            val register = getUserFromDataBase()
            _dineroPerdido.value = register.puntosCastigo * Precios.ACTIVIDAD_NEGATIVA.value
            _dineroGanado.value = register.puntosPremio * Precios.ACTIVIDAD_POSITIVA.value
            _dineroTotal.value = register.dinero
            register.fechaACtual = _fecha.value.toString()
            register.vidas = vidas
            register.vidasAntes = _vidasAntes.value!!
            val cad = stringUserLog(register)
            Log.i("hijo", "registro - Datos : $cad")

            update(register)

        }
    }

    private fun formatGMTDate(): String {
        val date: Date = Date()
        val dateFormat = DateFormat.format("dd/MM/yyyy HH:mm:ss", date)
        return dateFormat.toString()
    }

    private fun stringUserLog(userAux: Hijo): String {
        return "ID: ${userAux.hijoId}\n" +
                "NOMBRE: ${userAux.nombre}\n" +
                "FOTO: ${userAux.photoResourceId}\n" +
                "FECHA: ${userAux.fecha}\n" +
                "FECHA ACTUAL: ${userAux.fechaACtual}\n" +
                "PTS PREMIO: ${userAux.puntosPremio}\n" +
                "PTS CASTIGO: ${userAux.puntosCastigo}\n" +
                "PTS JUEGO: ${userAux.puntosJuego}\n" +
                "PTS AYER: ${userAux.dineroAntes}\n" +
                "PTS HOY: ${userAux.dineroUltimo}\n" +
                "DINERO: ${userAux.dinero}\n" +
                "VIDAS: ${userAux.vidas}"
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
