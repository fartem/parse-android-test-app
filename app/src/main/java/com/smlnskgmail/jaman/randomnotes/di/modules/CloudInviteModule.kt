package com.smlnskgmail.jaman.randomnotes.di.modules

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudInvite
import dagger.Module
import dagger.Provides

@Module
class CloudInviteModule(
    private val cloudInvite: CloudInvite
) {

    @Provides
    fun cloudInvites(): CloudInvite {
        return cloudInvite
    }

}
