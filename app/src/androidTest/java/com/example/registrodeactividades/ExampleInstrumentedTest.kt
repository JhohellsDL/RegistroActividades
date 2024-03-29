package com.example.registrodeactividades

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.database.Hijo
import com.example.registrodeactividades.database.HijosDataBase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
        assertEquals(hijo?.dineroUltimo, 0)
    }
}