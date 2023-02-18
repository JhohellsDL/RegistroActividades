package com.example.contadorcasino.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HijosDataBaseDao {
    @Insert
    fun insert(hijo: Hijo)

    @Update
    fun update(hijo: Hijo)

    @Query("SELECT * FROM hijos_table WHERE hijoId = :key")
    fun get(key: Long): Hijo

    @Query(value = "DELETE FROM hijos_table")
    fun clear()

    @Query(value = "SELECT * FROM hijos_table ORDER BY hijoId DESC")
    fun getAllHijos(): LiveData<List<Hijo>>

    @Query(value = "SELECT * FROM hijos_table ORDER BY hijoId DESC LIMIT 1")
    fun getHijoUser(): Hijo?
}