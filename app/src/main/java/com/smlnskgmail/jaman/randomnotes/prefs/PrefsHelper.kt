package com.smlnskgmail.jaman.randomnotes.prefs

import android.content.Context
import androidx.preference.PreferenceManager
import com.smlnskgmail.jaman.randomnotes.R

object PrefsHelper {

    fun isFirstLaunch(context: Context) = getBooleanPreference(context,
        context.getString(R.string.key_first_launch), true)

    fun setFirstLaunchStatus(context: Context, value: Boolean) {
        saveBooleanPreference(context, context.getString(R.string.key_first_launch), value)
    }

    private fun getBooleanPreference(context: Context, key: String, defaultValue: Boolean)
            = PreferenceManager.getDefaultSharedPreferences(context)
        .getBoolean(key, defaultValue)

    private fun saveBooleanPreference(context: Context, key: String, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit().putBoolean(key, value).apply()
    }

}