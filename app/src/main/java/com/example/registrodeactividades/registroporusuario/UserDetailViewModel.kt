package com.example.registrodeactividades.registroporusuario

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registrodeactividades.model.UserData
import com.example.registrodeactividades.model.mapToUserData
import com.example.registrodeactividades.providers.UserProvider

class UserDetailViewModel(
    private val userProvider: UserProvider,
    private val userId: String
) : ViewModel() {

    private var _currentUser = MutableLiveData<UserData>()
    val currentUser: LiveData<UserData>
        get() = _currentUser

    private var _isAdmin = MutableLiveData<Boolean>()
    val isAdmin: LiveData<Boolean>
        get() = _isAdmin

    init {
        getCurrentUser(userId)
    }

    private fun getCurrentUser(userId: String) {
        userProvider.getUserData(userId).addOnSuccessListener { it2 ->
            if (it2.exists()) {
                it2.data?.let {
                    _currentUser.value = mapToUserData(it)
                    Log.d("asdasd", "name: ${_currentUser.value!!.name}")
                    Log.d("asdasd", "water: ${_currentUser.value!!.consumeWater}")
                }
            }
        }
    }

    fun updateUser(newUser: UserData) {
        userProvider.updateCurrentUser(userId, newUser)
    }

    fun updateLivesInUser(newLives: Int) {
        _currentUser.value.let {
            val updateUser = it?.copy(lives = newLives)
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    fun updateDailyLivesInUser(newDailyLives: Int) {
        _currentUser.value.let {
            val updateUser = it?.copy(dailyLives = newDailyLives)
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    fun updateConsumeWaterUser(consumeWater: Int) {
        _currentUser.value.let {
            val updateUser = it?.copy(consumeWater = consumeWater)
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    fun updateDuolingo(duolingo: Boolean) {
        _currentUser.value.let {
            val updateUser = it?.copy(duolingo = duolingo)
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    fun updateAllFeatures(newLives: Int, newDailyLives: Int, consumeWater: Int, duolingo: Boolean) {
        _currentUser.value.let {
            val updateUser = it?.copy(
                lives = newLives,
                dailyLives = newDailyLives,
                duolingo = duolingo,
                consumeWater = consumeWater
            )
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }


    fun updateAllLivesInUser(newLives: Int, newDailyLives: Int) {
        _currentUser.value.let {
            val updateUser = it?.copy(
                lives = newLives,
                dailyLives = newDailyLives
            )
            userProvider.updateCurrentUser(userId, updateUser!!)
        }
    }

    fun setIsAdmin(value: Boolean) {
        _isAdmin.value = value
    }

}