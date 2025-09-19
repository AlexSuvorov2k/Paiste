package ru.alexsuvorov.paistewiki.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class AppPreferences @SuppressLint("CommitPrefEdits") constructor(context: Context) {
    private val _sharedPreferences: SharedPreferences
    private val _prefsEditor: SharedPreferences.Editor

    init {
        this._sharedPreferences = context.getSharedPreferences(AppPreferences.Companion.APP_SHARED_PREFS, Activity.MODE_PRIVATE)
        this._prefsEditor = _sharedPreferences.edit()
    }

    fun getText(key: String?): String {
        return _sharedPreferences.getString(key, "")!!
    }

    fun saveText(key: String?, text: String?) {
        _prefsEditor.putString(key, text)
        _prefsEditor.commit()
    }

    fun clear() {
        _prefsEditor.clear()
        _prefsEditor.commit()
    }

    companion object {
        private val APP_SHARED_PREFS: String = AppPreferences::class.java.getSimpleName()
    }
}
