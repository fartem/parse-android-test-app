package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.db.factory.DatabaseFactory
import com.smlnskgmail.jaman.randomnotes.parse.api.ParseApi
import com.smlnskgmail.jaman.randomnotes.parse.auth.ParseAuth

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseFactory.set(this)
        initializeParse()
    }

    private fun initializeParse() {
        val serverAddress = "SERVER_ADDRESS"
        val applicationId = "APP_ID"
        val clientKey = "CLIENT_KEY"

        ParseApi.initialize(this, serverAddress, applicationId, clientKey)
        ParseAuth.initialize(this)
    }

    override fun onTerminate() {
        DatabaseFactory.terminate()
        super.onTerminate()
    }

}