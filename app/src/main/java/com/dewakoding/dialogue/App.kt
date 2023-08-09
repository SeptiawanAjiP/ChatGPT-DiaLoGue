package com.dewakoding.dialogue

import android.app.Application
import android.content.Context
import com.dewakoding.dialogue.preference.Session

class App : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance : App? = null
        private var mSession: Session? = null
        fun context() : Context {
            return instance!!.applicationContext
        }

        fun getSession(): Session {
            return mSession!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        mSession = Session(this)
    }

}