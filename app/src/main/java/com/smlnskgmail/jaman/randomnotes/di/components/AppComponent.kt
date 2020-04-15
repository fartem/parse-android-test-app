package com.smlnskgmail.jaman.randomnotes.di.components

import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudInviteModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.view.auth.CloudAuthFragment
import com.smlnskgmail.jaman.randomnotes.view.invite.CloudInviteDialog
import com.smlnskgmail.jaman.randomnotes.view.list.NotesListFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        DataRepositoryModule::class,
        CloudAuthModule::class,
        CloudInviteModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)
    fun inject(notesListFragment: NotesListFragment)
    fun inject(cloudAuthFragment: CloudAuthFragment)
    fun inject(cloudInviteDialog: CloudInviteDialog)

}
