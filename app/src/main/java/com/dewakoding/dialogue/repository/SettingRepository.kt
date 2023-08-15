package com.dewakoding.dialogue.repository

import androidx.lifecycle.MutableLiveData
import com.dewakoding.dialogue.App
import com.dewakoding.dialogue.util.CommonCons.Companion.AUTO_SPEECH
import com.dewakoding.dialogue.util.CommonCons.Companion.SPEECH_SPEED


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class SettingRepository {

    fun setAutoSpeech(isActive: Boolean) {
        App.getSession().createSessionBool(AUTO_SPEECH, isActive)
    }

    fun isAutoSpeech(): Boolean {
        return App.getSession().getSessionBool(AUTO_SPEECH)
    }
}