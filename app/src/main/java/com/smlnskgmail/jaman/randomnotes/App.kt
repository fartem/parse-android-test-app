package com.smlnskgmail.jaman.randomnotes

import android.app.Application
import com.smlnskgmail.jaman.randomnotes.di.components.AppComponent
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerAppComponent
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudInviteModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.model.DataRepository
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudInvite
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake.FakeCloudAuth
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake.FakeCloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake.FakeCloudInvite
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.parse.ParseServerAuth
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.parse.ParseServerDataSource
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.parse.ParseServerInvite
import com.smlnskgmail.jaman.randomnotes.model.impl.ormlite.OrmLiteDataSource
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
        val cloudInvite: CloudInvite
        @Suppress("ConstantConditionIf")
        if (BuildConfig.CLOUD_REPOSITORY == "PARSE") {
            cloudDataSource = ParseServerDataSource(this)
            cloudAuth = ParseServerAuth(this)
            cloudInvite = ParseServerInvite()
        } else {
            cloudDataSource = FakeCloudDataSource()
            cloudAuth = FakeCloudAuth()
            cloudInvite = FakeCloudInvite()
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
            .cloudInviteModule(
                CloudInviteModule(
                    cloudInvite
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
