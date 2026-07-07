package ru.alexsuvorov.paistewiki.tools

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class AppPreferences @SuppressLint("CommitPrefEdits") constructor(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    private val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()

    fun getText(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun saveText(key: String, text: String) {
        prefsEditor.putString(key, text)
        prefsEditor.commit()
    }

    fun clear() {
        prefsEditor.clear()
        prefsEditor.commit()
    }

    companion object {
        private val APP_SHARED_PREFS = AppPreferences::class.java.simpleName
    }
}