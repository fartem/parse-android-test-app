package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.db.DatabaseInitializer

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseInitializer.set(this)
    }

    override fun onTerminate() {
        DatabaseInitializer.terminate()
        super.onTerminate()
    }

}