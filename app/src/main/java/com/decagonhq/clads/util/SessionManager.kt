package com.decagonhq.clads.util

import android.content.SharedPreferences
import javax.inject.Inject

class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    /*Load details From Shared Preferences*/
    fun loadFromSharedPref(prefType: String): String {
        return sharedPreferences.getString(prefType, "").toString()
    }

    /*Load logout boolean From Shared Preferences*/
    fun loadLogoutBooleanFromSharedPref(prefType: String): Boolean {
        return sharedPreferences.getBoolean(prefType, true)
    }

    /*Save details to Shared Preferences*/
    fun saveToSharedPref(prefType: String, prefValue: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(prefType, prefValue)
        editor.apply()
    }

    fun saveBooleanToSharedPref(prefType: String, prefValue: Boolean) {
        sharedPreferences.edit().putBoolean(prefType, prefValue).apply()
    }

    /*Clear values in Shared Preferences*/
    fun clearSharedPref() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
