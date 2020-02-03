package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.di.components.ApplicationComponent
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerApplicationComponent
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake.FakeCloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake.FakeCloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse.ParseAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse.ParseDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.local.ormlite.OrmLiteDataSource
import javax.inject.Inject

class Application : Application() {

    companion object {

        lateinit var applicationComponent: ApplicationComponent

    }

    @Inject
    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()
        productionStart()
    }

    private fun productionStart() {
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

    @Suppress("unused")
    private fun testStart() {
        applicationComponent = DaggerApplicationComponent.builder()
            .withDataRepository(
                DataRepositoryModule(
                    OrmLiteDataSource(this),
                    FakeCloudDataSource()
                )
            )
            .withCloudAuth(
                CloudAuthModule(
                    FakeCloudAuth()
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
