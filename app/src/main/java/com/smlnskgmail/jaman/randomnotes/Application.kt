package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.db.DatabaseFactory
import com.smlnskgmail.jaman.randomnotes.prefs.PrefsHelper

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseFactory.set(this)
        if (PrefsHelper.isFirstLaunch(this)) {
            PrefsHelper.setFirstLaunchStatus(this, false)
        }
    }

    override fun onTerminate() {
        DatabaseFactory.terminate()
        super.onTerminate()
    }

}