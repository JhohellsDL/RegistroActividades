package com.example.registrodeactividades.detallevidas

import androidx.lifecycle.ViewModel
import com.example.contadorcasino.database.HijosDataBaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class VidasViewModel(
    val database: HijosDataBaseDao
) : ViewModel() {

    //-----------------------------------para coroutinas------------------------------------------------
    /*private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)*/
    private val viewModeJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)
    //--------------------------------------------------------------------------------------------------

    init {

    }

    override fun onCleared() {
        super.onCleared()
        viewModeJob.cancel()
    }
}