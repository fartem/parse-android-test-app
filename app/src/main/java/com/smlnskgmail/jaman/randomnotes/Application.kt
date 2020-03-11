package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.di.components.ApplicationComponent
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerApplicationComponent
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudDataSource
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
        configureRepositories()
    }

    private fun configureRepositories() {
        val cloudDataSource: CloudDataSource
        val cloudAuth: CloudAuth
        @Suppress("ConstantConditionIf")
        if (BuildConfig.CLOUD_REPOSITORY == "PARSE") {
            cloudDataSource = ParseDataSource(this)
            cloudAuth = ParseAuth()
        } else {
            cloudDataSource = FakeCloudDataSource()
            cloudAuth = FakeCloudAuth()
        }

        applicationComponent = DaggerApplicationComponent.builder()
            .withDataRepository(
                DataRepositoryModule(
                    OrmLiteDataSource(this),
                    cloudDataSource
                )
            )
            .withCloudAuth(
                CloudAuthModule(
                    cloudAuth
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
