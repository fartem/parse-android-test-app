package com.smlnskgmail.jaman.randomnotes.di

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.CloudAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CloudAuthModule(
    private val cloudAuth: CloudAuth
) {

    @Provides
    @Singleton
    fun cloudAuth(): CloudAuth {
        return cloudAuth;
    }

}
