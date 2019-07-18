package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.parse.Parse
import com.smlnskgmail.jaman.randomnotes.db.support.DatabaseFactory
import com.smlnskgmail.jaman.randomnotes.prefs.PrefsHelper

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        if (PrefsHelper.isFirstLaunch(this)) {
            PrefsHelper.setFirstLaunchStatus(this, false)
        }
        DatabaseFactory.set(this)
        initializeParse()
    }

    private fun initializeParse() {
        val parseConfig = Parse.Configuration.Builder(this)
            .applicationId("")
            .clientKey("12321")
            .server("http://xxx.xxx.xx.x:1337/parse/")
            .build()
        Parse.initialize(parseConfig)
    }

    override fun onTerminate() {
        DatabaseFactory.terminate()
        super.onTerminate()
    }

}