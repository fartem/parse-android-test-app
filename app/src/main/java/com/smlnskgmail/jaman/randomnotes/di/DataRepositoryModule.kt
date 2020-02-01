package com.smlnskgmail.jaman.randomnotes.di

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.LocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
