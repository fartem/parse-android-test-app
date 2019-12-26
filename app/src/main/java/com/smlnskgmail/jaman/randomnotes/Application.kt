package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.di.ApplicationComponent
import com.smlnskgmail.jaman.randomnotes.di.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.DaggerApplicationComponent
import com.smlnskgmail.jaman.randomnotes.di.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.sources.ormlite.OrmLiteDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.sources.parse.ParseAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.sources.parse.ParseDataSource
import javax.inject.Inject

@Suppress("unused")
class Application : Application() {

    companion object {

        lateinit var applicationComponent: ApplicationComponent

    }

    @Inject
    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .withDataRepository(
                DataRepositoryModule(
                    OrmLiteDataSource(this),
                    ParseDataSource(this)
                )
            )
            .withCloudAuth(
                CloudAuthModule(
                    ParseAuth()
                )
            )
            .build()
        applicationComponent.inject(this)
    }

    override fun onTerminate() {
        dataRepository.destroy()
        super.onTerminate()
    }

}
