package com.smlnskgmail.jaman.randomnotes.di.modules

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class CloudAuthModule(
    private val cloudAuth: CloudAuth
) {

    @Provides
    @Singleton
    fun cloudAuth(): CloudAuth {
        return cloudAuth
    }

}
