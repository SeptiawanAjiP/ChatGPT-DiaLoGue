package com.dewakoding.dialogue.ui.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dewakoding.dialogue.database.AppDatabase
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class SessionViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SessionRepository

    val allNotes: LiveData<List<Session>>

    init {

        val dao = AppDatabase.getDatabase(application).sessionDao()
        repository = SessionRepository(dao)
        allNotes = repository.allSession
    }

    fun deleteNote(session: Session) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(session)
    }

    fun insertNote(session: Session) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(session)
    }

    fun updateNote(session: Session) =  viewModelScope.launch(Dispatchers.IO) {
        repository.update(session)
    }

}