package com.example.registrodeactividades

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.registrodeactividades.database.Hijo
import com.example.registrodeactividades.database.HijosDataBase
import com.example.contadorcasino.database.HijosDataBaseDao
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var sleepDao: HijosDataBaseDao
    private lateinit var db: HijosDataBase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, HijosDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        sleepDao = db.hijosDataBaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = Hijo()
        sleepDao.insert(night)
        val hijo = sleepDao.getHijoUser()
        assertEquals(hijo?.puntosHoy, 0)
    }
}