package com.example.registrodeactividades.actividades

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contadorcasino.database.Hijo
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.model.AccionPositiva
import kotlinx.coroutines.*

class ActividadesViewModel(
    val dataBaseDao: HijosDataBaseDao,
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

    private val _myPositiveDataset = MutableLiveData<List<AccionPositiva>>()
    val myPositiveDataset: LiveData<List<AccionPositiva>>
        get() = _myPositiveDataset


    //-----------------------------------para coroutinas------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope( Dispatchers.Main +viewModelJob)
    //--------------------------------------------------------------------------------------------------

    init {
        _myPositiveDataset.value = DataSource().loadPositiveActions()
        Log.i("hijo"," lista dentro view model ${_myPositiveDataset.value}")
        _ptsGanados.value = 0
        _contadorItem.value = 0
        initializeUser()
    }

    private fun initializeUser() {
        uiScope.launch {
            _user.value = getUserFromDataBase()
        }
    }

    private suspend fun getUserFromDataBase(): Hijo? {
        return withContext(Dispatchers.IO){
            val pUser = dataBaseDao.get(userId)
            Log.i("hijo","--pUser ${pUser.nombre}")
            pUser
        }
    }

    fun actualizarRegistroUsuario(){
        uiScope.launch {
            val pRegistro = getUserFromDataBase2()
            pRegistro.nombre = "nombre Registrado"
            actualizar(pRegistro)

        }
    }

    private suspend fun actualizar(hijo: Hijo) {
        withContext(Dispatchers.IO){
            dataBaseDao.update(hijo)
            Log.i("hijo","Actualizado -- ${hijo.nombre}")
        }
    }

    private suspend fun getUserFromDataBase2(): Hijo {
        return withContext(Dispatchers.IO){
            val pHijo = dataBaseDao.get(userId)
            pHijo
        }
    }

    fun onAccionPositivaClicked(id: Int){
        _ptsGanados.value = _ptsGanados.value!! + id
    }
    fun onAccionNegativaClicked(id: Int){
        _ptsGanados.value = _ptsGanados.value!!- id
    }
    fun onContadorItemClicked(){
        _contadorItem.value = _contadorItem.value!! + 1
    }

    fun setItemList(list: List<AccionPositiva>) {
        _myPositiveDataset.value = list
    }

    fun getItemList(): List<AccionPositiva> {
        return _myPositiveDataset.value!!
    }

    fun onSleepDataQualityNavigated() {
        _ptsGanados.value = null
    }
}