package com.smlnskgmail.jaman.randomnotes.di.components

import com.smlnskgmail.jaman.randomnotes.Application
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.logic.login.LoginFragment
import com.smlnskgmail.jaman.randomnotes.logic.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataRepositoryModule::class,
        CloudAuthModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    fun inject(application: Application)
    fun inject(mainFragment: MainFragment)
    fun inject(loginFragment: LoginFragment)

    @Component.Builder
    interface Builder {

        fun withDataRepository(
            dataRepositoryModule: DataRepositoryModule
        ): Builder

        fun withCloudAuth(
            cloudAuthModule: CloudAuthModule
        ): Builder

        fun build(): ApplicationComponent

    }

}
