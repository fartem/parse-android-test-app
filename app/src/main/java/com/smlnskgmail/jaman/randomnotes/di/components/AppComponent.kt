package com.smlnskgmail.jaman.randomnotes.di.components

import com.smlnskgmail.jaman.randomnotes.App
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
interface AppComponent {

    fun inject(app: App)
    fun inject(mainFragment: MainFragment)
    fun inject(loginFragment: LoginFragment)

}
