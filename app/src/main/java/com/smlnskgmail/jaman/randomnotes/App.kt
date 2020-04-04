package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.di.components.AppComponent
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerAppComponent
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

class App : Application() {

    lateinit var appComponent: AppComponent

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

        appComponent = DaggerAppComponent.builder()
            .dataRepositoryModule(
                DataRepositoryModule(
                    OrmLiteDataSource(this),
                    cloudDataSource
                )
            )
            .cloudAuthModule(
                CloudAuthModule(
                    cloudAuth
                )
            )
            .build()
        appComponent.inject(this)
    }

    override fun onTerminate() {
        dataRepository.destroy()
        super.onTerminate()
    }

}
