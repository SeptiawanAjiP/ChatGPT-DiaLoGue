package com.dewakoding.dialogue.repository

import androidx.lifecycle.LiveData
import com.dewakoding.dialogue.database.dao.SessionDao
import com.dewakoding.dialogue.database.entity.Session


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class SessionRepository(private val sessionDao: SessionDao) {

    val allSession: LiveData<List<Session>> = sessionDao.getAllSession()

    suspend fun insert(ses: Session) {
        sessionDao.insert(ses)
    }

    suspend fun delete(session: Session) {
        sessionDao.delete(session)
    }

    suspend fun update(session: Session) {
        sessionDao.update(
            session.id,
            session.title,
            session.description
        )
    }

}