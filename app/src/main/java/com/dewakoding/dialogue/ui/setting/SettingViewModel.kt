package com.dewakoding.dialogue.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dewakoding.dialogue.repository.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class SettingViewModel: ViewModel() {

    fun setAutoSpeech(isActive: Boolean) {
        SettingRepository().setAutoSpeech(isActive)
    }

    fun isAutoSpeech(): Boolean {
        return SettingRepository().isAutoSpeech()
    }
}