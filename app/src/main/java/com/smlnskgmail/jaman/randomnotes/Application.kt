package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.parse.Parse
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.db.factory.DatabaseFactory

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseFactory.set(this)
        initializeParse()
    }

    private fun initializeParse() {
        val applicationId = "APP_ID"
        val serverAddress = "SERVER_ADDRESS"
        val clientKey = "CLIENT_KEY"

        val parseConfig = Parse.Configuration.Builder(this)
            .applicationId(applicationId)
            .clientKey(clientKey)
            .server(serverAddress)
            .build()
        Parse.initialize(parseConfig)
        ParseFacebookUtils.initialize(this)
    }

    override fun onTerminate() {
        DatabaseFactory.terminate()
        super.onTerminate()
    }

}