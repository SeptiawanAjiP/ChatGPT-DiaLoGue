package com.dewakoding.dialogue.preference

import android.content.Context
import android.content.SharedPreferences

class Session(private val _context: Context) {
    internal var preferences: SharedPreferences
    internal var editor: SharedPreferences.Editor

    private val PREF_NAME = "dialogue"
    private val PRIVATE_MODE = 0


    init {
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = preferences.edit()
    }

    fun createSessionString(name: String, value: String?) {
        editor.putString(name, value)
        editor.commit()
    }

    fun getSessionString(name: String): String? {
        return preferences.getString(name, null)
    }

    fun createSessionBool(name: String, value: Boolean) {
        editor.putBoolean(name, value)
        editor.commit()
    }

    fun getSessionBool(name: String): Boolean {
        return preferences.getBoolean(name, false)
    }

    fun clearSession() {
        editor.clear()
        editor.commit()
    }

    fun removeSession(name: String) {
        editor.remove(name)
        editor.commit()
    }

}