package ru.alexsuvorov.paistewiki.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String APP_SHARED_PREFS = AppPreferences.class.getSimpleName();

    private SharedPreferences _sharedPreferences;
    private SharedPreferences.Editor _prefsEditor;

    @SuppressLint("CommitPrefEdits")
    public AppPreferences(Context context) {
        this._sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPreferences.edit();
    }

    public String getText(String key) {
        return _sharedPreferences.getString(key, "");
    }

    public void saveText(String key, String text) {
        _prefsEditor.putString(key, text);
        _prefsEditor.commit();
    }

    public void clear() {
        _prefsEditor.clear();
        _prefsEditor.commit();
    }
}
