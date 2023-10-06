package com.dewakoding.dialogue.data.local

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dewakoding.dialogue.LiveDataTestUtil
import com.dewakoding.dialogue.data.AppDatabase
import com.dewakoding.dialogue.data.dao.SessionDao
import com.dewakoding.dialogue.data.entity.Session
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SessionDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: SessionDao


    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.sessionDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSession() = runBlockingTest {
        val session = Session(null, "Title 1", "Description 1", Date().time)
        dao.insert(session)
        val session1 = Session(null, "Title 2", "Description 2", Date().time)
        dao.insert(session1)

        val allSessionLiveData = dao.getAllSession()
        val allSession = LiveDataTestUtil.getValue(allSessionLiveData)

        Assert.assertTrue(allSession.size == 2)
    }
}