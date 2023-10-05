package com.example.registrodeactividades.actividades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.database.Hijo
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionPositiva
import com.example.registrodeactividades.model.UserData
import com.example.registrodeactividades.model.mapToUserData
import com.example.registrodeactividades.providers.UserProvider
import com.example.registrodeactividades.utils.Precios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActividadesViewModel(
    private val userProvider: UserProvider,
    private val userId: String
) : ViewModel() {

    private var _currentUser = MutableLiveData<UserData>()
    val currentUser: LiveData<UserData>
        get() = _currentUser

    private var _user = MutableLiveData<Hijo>()
    val user: LiveData<Hijo>
        get() = _user

    private var _ptsGanados = MutableLiveData<Int>()
    val  ptsGanados: LiveData<Int>
        get() = _ptsGanados

    private var _ptsPerdidos = MutableLiveData<Int>()
    val ptsPerdidos: LiveData<Int>
        get() = _ptsPerdidos

    private var _dineroTotal = MutableLiveData<Float>()
    val dineroTotal: LiveData<Float>
        get() = _dineroTotal

    private var _dineroGanado = MutableLiveData<Float>()
    val dineroGanado: LiveData<Float>
        get() = _dineroGanado

    private var _dineroAntes = MutableLiveData<Float>()
    val dineroAntes: LiveData<Float>
        get() = _dineroAntes

    private var _dineroUltimo = MutableLiveData<Float>()
    val dineroUltimo: LiveData<Float>
        get() = _dineroUltimo

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
            getCurrentUser(userId)
        }
    }

    private fun getCurrentUser(userId: String) {
        userProvider.getUserData(userId).addOnSuccessListener { it2 ->
            if (it2.exists()) {
                it2.data?.let {
                    _currentUser.value = mapToUserData(it)
                }
            }
        }
    }

    /*
    private suspend fun getUserFromDataBase(): Hijo {
        return withContext(Dispatchers.IO) {
            val pUser = dataBaseDao.get(userId)
            val cad = stringUserLog(pUser)
            Log.i("hijo", "userAux : $cad")
            pUser
        }
    }

     */

    //************ editar**********************************
    fun updateMoneyInUser(currentMoney: String, lostMoney: String, recentMoney: String) {
        _currentUser.value.let {
            val updateUser = it?.copy(
                currentMoney = currentMoney,
                lostMoney = lostMoney,
                recentMoney = recentMoney
            )
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    fun updateMoneyAndPoints(currentMoney: String, lostMoney: String, recentMoney: String, pointsEarned: Int, pointsLost: Int) {
        _currentUser.value.let {
            val updateUser = it?.copy(
                currentMoney = currentMoney,
                lostMoney = lostMoney,
                recentMoney = recentMoney,
                pointsEarned = pointsEarned,
                pointsLost = pointsLost
            )
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    private fun registroDatos() {
        uiScope.launch {
            _currentUser.value.let {

            }
            /*
            val register = get(userId)
            register.puntosPremio = register.puntosPremio + _ptsGanados.value!!
            register.puntosCastigo = register.puntosCastigo + _ptsPerdidos.value!!
            register.dineroAntes = _dineroAntes.value!!
            register.dineroUltimo =  _dineroTotal.value!!
            register.dinero = register.dinero + _dineroTotal.value!!

            update(register)
            initializeUser()
            iniciarEnCero()

             */
        }
    }

    private fun iniciarEnCero() {
        _ptsGanados.value = 0
        _ptsPerdidos.value = 0
        _dineroTotal.value = 0.0f
        _dineroGanado.value = 0f
        _dineroPerdido.value = 0f
    }

    /*
    private suspend fun get(id: Long): Hijo {
        return withContext(Dispatchers.IO) {
            val userAux = dataBaseDao.get(id)
            userAux
        }
    }

     */

    private suspend fun update(registro: Hijo) {
        withContext(Dispatchers.IO) {
            //dataBaseDao.update(registro)
        }
    }
    //*****************************************************

    fun onAccionPositivaClicked(valorActividad: Int) {
        _ptsGanados.value = _ptsGanados.value!! + valorActividad
        _dineroGanado.value =
            _dineroGanado.value!! + (Precios.ACTIVIDAD_POSITIVA.value * valorActividad)
        _dineroTotal.value = _dineroGanado.value!! - _dineroPerdido.value!!
    }

    fun onAccionNegativaClicked(valorActividad: Int) {
        _ptsPerdidos.value = _ptsPerdidos.value!! + valorActividad
        _dineroPerdido.value =
            _dineroPerdido.value!! + (Precios.ACTIVIDAD_NEGATIVA.value * valorActividad)
        _dineroTotal.value = _dineroGanado.value!! - _dineroPerdido.value!!
    }

    fun setItemPositiveList(list: List<AccionPositiva>) {
        _myPositiveDataset.value = list
    }

    fun setItemNegativeList(list: List<AccionNegativa>) {
        _myNegativeDataset.value = list
    }

    fun saveDatos() {
        registroDatos()
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
        return "ID: ${userAux.hijoId}\n" +
                "NOMBRE: ${userAux.nombre}\n" +
                "FOTO: ${userAux.photoResourceId}\n" +
                "FECHA: ${userAux.fecha}\n" +
                "PTS PREMIO: ${userAux.puntosPremio}\n" +
                "PTS CASTIGO: ${userAux.puntosCastigo}\n" +
                "PTS JUEGO: ${userAux.puntosJuego}\n" +
                "PTS AYER: ${userAux.dineroAntes}\n" +
                "PTS HOY: ${userAux.dineroUltimo}\n" +
                "DINERO: ${userAux.dinero}\n" +
                "VIDAS: ${userAux.vidas}"
    }
}