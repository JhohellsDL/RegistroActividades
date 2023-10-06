package com.example.registrodeactividades.detalleusuario

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.registrodeactividades.database.Hijo
import com.example.registrodeactividades.database.dao.AccionNegativaDao
import com.example.registrodeactividades.database.dao.AccionNegativaMatthewDao
import com.example.registrodeactividades.database.dao.AccionPositivaDao
import com.example.registrodeactividades.database.dao.AccionPositivaMatthewDao
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionNegativaMatthew
import com.example.registrodeactividades.model.AccionPositiva
import com.example.registrodeactividades.model.AccionPositivaMatthew
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleUsuarioViewModel(
    val dataAccionPositivaDao: AccionPositivaDao,
    val dataAccionNegativaDao: AccionNegativaDao,
    val dataAccionPositivaMatthewDao: AccionPositivaMatthewDao,
    val dataAccionNegativaMatthewDao: AccionNegativaMatthewDao,
    application: Application
) : AndroidViewModel(application) {

    private var _hijo = MutableLiveData<Hijo>()
    val hijo: LiveData<Hijo>
        get() = _hijo

    private var _hMatthew = MutableLiveData<Hijo>()
    val hMatthew: LiveData<Hijo>
        get() = _hMatthew

    private var _listPositivasAndrew = MutableLiveData<List<AccionPositiva>>()
    val listPositivasAndrew: LiveData<List<AccionPositiva>>
        get() = _listPositivasAndrew

    private var _listPositivasMatthew = MutableLiveData<List<AccionPositivaMatthew>>()
    val listPositivasMatthew: LiveData<List<AccionPositivaMatthew>>
        get() = _listPositivasMatthew

    private var _listNegativasAndrew = MutableLiveData<List<AccionNegativa>>()
    val listNegativasAndrew: LiveData<List<AccionNegativa>>
        get() = _listNegativasAndrew

    private var _listNegativasMatthew = MutableLiveData<List<AccionNegativaMatthew>>()
    val listNegativasMatthew: LiveData<List<AccionNegativaMatthew>>
        get() = _listNegativasMatthew


    var listaPositiva = listOf<AccionPositiva>()

    //-----------------------------------para coroutinas------------------------------------------------
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //--------------------------------------------------------------------------------------------------

    init {
        getlistasAndrew()
    }

    fun getlistasAndrew() {
        uiScope.launch {
            _listPositivasAndrew.postValue(getListPositivas())
            _listNegativasAndrew.postValue(getListNegativas())
        }
    }

    fun getListasMatthew() {
        uiScope.launch {
            _listPositivasMatthew.postValue(getListPositivasMatthew())
            _listNegativasMatthew.postValue(getListNegativasMatthew())
        }
    }

    private suspend fun getListPositivas(): List<AccionPositiva> {
        return withContext(Dispatchers.IO) {
            val listaPosAndrew = dataAccionPositivaDao.getAll()
            listaPosAndrew
        }
    }
    private suspend fun getListNegativas(): List<AccionNegativa> {
        return withContext(Dispatchers.IO) {
            dataAccionNegativaDao.getAll()
        }
    }
    private suspend fun getListPositivasMatthew(): List<AccionPositivaMatthew> {
        return withContext(Dispatchers.IO) {
            dataAccionPositivaMatthewDao.getAll()
        }
    }
    private suspend fun getListNegativasMatthew(): List<AccionNegativaMatthew> {
        return withContext(Dispatchers.IO) {
            dataAccionNegativaMatthewDao.getAll()
        }
    }



    fun insertarAccionAndrew(accionPositiva: AccionPositiva) {
        uiScope.launch {
            insertPositiva(accionPositiva)
        }
    }

    fun insertarAccionMatthew(accionPositivaMatthew: AccionPositivaMatthew) {
        uiScope.launch {
            insertPositivaMatthew(accionPositivaMatthew)
        }
    }

    fun insertarAccionNegativaAndrew(accionNegativa: AccionNegativa) {
        uiScope.launch {
            insertNegativa(accionNegativa)
        }
    }

    fun insertarAccionNegativaMatthew(accionNegativa: AccionNegativaMatthew) {
        uiScope.launch {
            insertNegativaMatthew(accionNegativa)
        }
    }

    private suspend fun insertPositiva(accionPositiva: AccionPositiva) {
        withContext(Dispatchers.IO) {
            dataAccionPositivaDao.insert(accionPositiva)
        }
    }

    private suspend fun insertNegativa(accionNegativa: AccionNegativa) {
        withContext(Dispatchers.IO) {
            dataAccionNegativaDao.insert(accionNegativa)
        }
    }

    private suspend fun insertPositivaMatthew(accionPositiva: AccionPositivaMatthew) {
        withContext(Dispatchers.IO) {
            dataAccionPositivaMatthewDao.insert(accionPositiva)
        }
    }

    private suspend fun insertNegativaMatthew(accionNegativa: AccionNegativaMatthew) {
        withContext(Dispatchers.IO) {
            dataAccionNegativaMatthewDao.insert(accionNegativa)
        }
    }

    private suspend fun limpiarRegistroAndrew() {
        withContext(Dispatchers.IO) {

        }
    }

    private suspend fun limpiarRegistroMatthew() {
        uiScope.launch {

        }
    }

    private val _idUserForNavigation = MutableLiveData<Long?>()
    val idUserForNavigation
        get() = _idUserForNavigation

    fun onUserClicked(id: Long) {
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