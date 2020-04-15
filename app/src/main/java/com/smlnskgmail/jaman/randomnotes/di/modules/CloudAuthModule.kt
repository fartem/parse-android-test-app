package com.smlnskgmail.jaman.randomnotes.di.modules

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.utils.OpenForTests
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@OpenForTests
@Module
class CloudAuthModule(
    private val cloudAuth: CloudAuth
) {

    @Provides
    @Singleton
    fun cloudAuth(): CloudAuth {
        return cloudAuth
    }

}
