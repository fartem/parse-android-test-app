package com.smlnskgmail.jaman.randomnotes.di.modules

import com.smlnskgmail.jaman.randomnotes.model.DataRepository
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.api.local.LocalDataSource
import com.smlnskgmail.jaman.randomnotes.utils.OpenForTests
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@OpenForTests
@Module
class DataRepositoryModule(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource
) {

    @Provides
    @Singleton
    fun dataRepository(): DataRepository {
        return DataRepository(
            localDataSource,
            cloudDataSource
        )
    }

}
