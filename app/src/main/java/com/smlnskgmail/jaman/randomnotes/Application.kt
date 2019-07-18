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
        val applicationId = "LFpLIWo7tzOz0XUZtlX1xVN2NHfgpV9tFWetn5Sd"
        val serverAddress = "http://192.168.10.181:1337/parse/"
        val clientKey = "12321"

        val parseConfig = Parse.Configuration.Builder(this)
            .applicationId(applicationId)
            .clientKey(clientKey)
            .server(serverAddress)
            .build()
        Parse.initialize(parseConfig)
    }

    override fun onTerminate() {
        DatabaseFactory.terminate()
        super.onTerminate()
    }

}