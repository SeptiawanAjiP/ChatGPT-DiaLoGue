package com.dewakoding.dialogue.ui.session

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewakoding.dialogue.database.AppDatabase
import com.dewakoding.dialogue.database.entity.Session
import com.dewakoding.dialogue.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
@HiltViewModel
class SessionViewModel @Inject constructor(val sessionRepository: SessionRepository): ViewModel() {

    val allNotes: LiveData<List<Session>>

    init {
        allNotes = sessionRepository.allSession
    }

    fun deleteNote(session: Session) = viewModelScope.launch(Dispatchers.IO) {
        sessionRepository.delete(session)
    }

    fun insertNote(session: Session) = viewModelScope.launch(Dispatchers.IO) {
        sessionRepository.insert(session)
    }

    fun updateNote(session: Session) =  viewModelScope.launch(Dispatchers.IO) {
        sessionRepository.update(session)
    }

}