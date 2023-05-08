package com.example.registrodeactividades.actividades

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrodeactividades.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionPositiva
import com.example.registrodeactividades.utils.Precios
import kotlinx.coroutines.*

class ActividadesViewModel(
    private val dataBaseDao: HijosDataBaseDao,
    private val userId: Long
) : ViewModel() {

    private var _user = MutableLiveData<Hijo>()
    val user: LiveData<Hijo>
        get() = _user

    private var _contadorItem = MutableLiveData<Int>()
    val contadorItem: LiveData<Int>
        get() = _contadorItem

    private var _ptsGanados = MutableLiveData<Int>()
    val ptsGanados: LiveData<Int>
        get() = _ptsGanados

    private var _ptsPerdidos = MutableLiveData<Int>()
    val ptsPerdidos: LiveData<Int>
        get() = _ptsPerdidos

    private var _ptsTotal = MutableLiveData<Int>()
    val ptsTotal: LiveData<Int>
        get() = _ptsTotal

    private var _dineroTotal = MutableLiveData<Float>()
    val dineroTotal: LiveData<Float>
        get() = _dineroTotal

    private var _dineroGanado = MutableLiveData<Float>()
    val dineroGanado: LiveData<Float>
        get() = _dineroGanado

    private var _dineroPerdido = MutableLiveData<Float>()
    val dineroPerdido: LiveData<Float>
        get() = _dineroPerdido

    private val _myPositiveDataset = MutableLiveData<List<AccionPositiva>>()
    val myPositiveDataset: LiveData<List<AccionPositiva>>
        get() = _myPositiveDataset

    private val _myNegativeDataset = MutableLiveData<List<AccionNegativa>>()
    val myNegativeDataset: LiveData<List<AccionNegativa>>
        get() = _myNegativeDataset

    private val _recyclerPositivoVisible = MutableLiveData<Boolean>()
    val recyclerPositivoVisible: LiveData<Boolean>
        get() = _recyclerPositivoVisible

    private val _recyclerNegativoVisible = MutableLiveData<Boolean>()
    val recyclerNegativoVisible: LiveData<Boolean>
        get() = _recyclerNegativoVisible


    //-----------------------------------para coroutinas------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //--------------------------------------------------------------------------------------------------

    init {
        _myPositiveDataset.value = DataSource().loadPositiveActions()
        _myNegativeDataset.value = DataSource().loadNegativeActions()

        _recyclerPositivoVisible.value = true
        _recyclerNegativoVisible.value = false

        iniciarEnCero()
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDataBase()
        }
    }

    private suspend fun getUserFromDataBase(): Hijo {
        return withContext(Dispatchers.IO) {
            val pUser = dataBaseDao.get(userId)
            val cad = stringUserLog(pUser)
            Log.i("hijo", "userAux : $cad")
            pUser
        }
    }

    //************ editar**********************************
    fun registroDatos() {
        uiScope.launch {
            val register = get(userId)
            register.puntosPremio = register.puntosPremio + _ptsGanados.value!!
            register.puntosCastigo = register.puntosCastigo + _ptsPerdidos.value!!
            register.puntosHoy = register.puntosHoy + _ptsTotal.value!!
            register.dinero = register.dinero + _dineroTotal.value!!
            Log.i(
                "hijo",
                "edit ganados1: ${register.puntosPremio} - ${register.puntosCastigo} - ${register.puntosHoy} - ${register.dinero}"
            )
            Log.i(
                "hijo",
                "funcion registroDatos : ${_ptsGanados.value} - ${_ptsPerdidos.value} - ${_ptsTotal.value} - ${_dineroTotal.value}"
            )
            update(register)

            initializeUser()

            iniciarEnCero()
        }
    }

    private fun iniciarEnCero() {
        _ptsGanados.value = 0
        _ptsPerdidos.value = 0
        _ptsTotal.value = 0
        _dineroTotal.value = 0.0f
        _dineroGanado.value = 0f
        _dineroPerdido.value = 0f
        _contadorItem.value = 0
    }

    private suspend fun get(id: Long): Hijo {
        return withContext(Dispatchers.IO) {
            val userAux = dataBaseDao.get(id)
            userAux
        }
    }

    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO) {
            dataBaseDao.update(registro)
        }
    }
    //*****************************************************

    fun onAccionPositivaClicked(valorActividad: Int) {
        Log.i("hijo","positive antes :_ ${_ptsGanados.value} - ${_ptsTotal.value} -- ${_dineroPerdido.value} -- ${_dineroGanado.value} --- ${_dineroTotal.value}")
        _ptsGanados.value = _ptsGanados.value!! + valorActividad
        _ptsTotal.value = _ptsTotal.value!! + valorActividad
        //_dineroTotal.value = _ptsTotal.value!! * Precios.ACTIVIDAD_POSITIVA.value //0.035f
        _dineroGanado.value = _dineroGanado.value!! + (Precios.ACTIVIDAD_POSITIVA.value * valorActividad)
        _dineroTotal.value = _dineroGanado.value!! - _dineroPerdido.value!!
        Log.i("hijo","positive despues :_ ${_ptsGanados.value} - ${_ptsTotal.value} -- ${_dineroPerdido.value} -- ${_dineroGanado.value} --- ${_dineroTotal.value}")
    }

    fun onAccionNegativaClicked(valorActividad: Int) {
        Log.i("hijo","negative antes :_ ${_ptsPerdidos.value} - ${_ptsTotal.value} -- ${_dineroPerdido.value} -- ${_dineroGanado.value} --- ${_dineroTotal.value}")
        _ptsPerdidos.value = _ptsPerdidos.value!! + valorActividad
        _ptsTotal.value = _ptsTotal.value!! - valorActividad
        //_dineroTotal.value = _ptsTotal.value!! * Precios.ACTIVIDAD_NEGATIVA.value //0.015f
        _dineroPerdido.value = _dineroPerdido.value!! + (Precios.ACTIVIDAD_NEGATIVA.value * valorActividad)
        _dineroTotal.value = _dineroGanado.value!! - _dineroPerdido.value!!
        Log.i("hijo","negative despues :_ ${_ptsPerdidos.value} - ${_ptsTotal.value} -- ${_dineroPerdido.value} -- ${_dineroGanado.value} --- ${_dineroTotal.value}")
    }

    fun setItemPositiveList(list: List<AccionPositiva>) {
        _myPositiveDataset.value = list
    }
    fun setItemNegativeList(list: List<AccionNegativa>) {
        _myNegativeDataset.value = list
    }

    fun getItemList(): List<AccionPositiva> {
        return _myPositiveDataset.value!!
    }

    fun saveDatos() {
        registroDatos()

        Log.i(
            "hijo",
            "edit ganados4: ${_ptsGanados.value} - ${_ptsPerdidos.value} - ${_ptsTotal.value} - ${_dineroTotal.value}"
        )
        /*_ptsGanados.value = 0
        _ptsPerdidos.value = 0
        _ptsTotal.value = 0
        _dineroTotal.value = 0.0f*/
    }

    fun onVisibleBuenas() {
        if (!_recyclerPositivoVisible.value!!) {
            _recyclerPositivoVisible.value = true
            _recyclerNegativoVisible.value = false
        }
    }
    fun onVisibleMalas() {
        if (_recyclerPositivoVisible.value!!) {
            _recyclerPositivoVisible.value = false
            _recyclerNegativoVisible.value = true
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

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
}