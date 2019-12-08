package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.logic.sources.ormlite.OrmLiteDataSource
import com.smlnskgmail.jaman.randomnotes.logic.sources.parse.ParseDataSource
import com.smlnskgmail.jaman.randomnotes.repository.DataRepositoryAccessor

@Suppress("unused")
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        DataRepositoryAccessor.initWith(
            OrmLiteDataSource(this),
            ParseDataSource(this)
        )
    }

    override fun onTerminate() {
        DataRepositoryAccessor.free()
        super.onTerminate()
    }

}