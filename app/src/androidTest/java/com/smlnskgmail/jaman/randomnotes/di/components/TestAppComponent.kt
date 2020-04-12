package com.smlnskgmail.jaman.randomnotes.di.components

import com.smlnskgmail.jaman.randomnotes.auth.BaseAuthTest
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.note.BaseNoteTest
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataRepositoryModule::class,
        CloudAuthModule::class
    ]
)
@Singleton
interface TestAppComponent : AppComponent {

    fun inject(baseNoteTest: BaseNoteTest)
    fun inject(baseAuthTest: BaseAuthTest)

}
