package com.smlnskgmail.jaman.randomnotes.di

import com.smlnskgmail.jaman.randomnotes.logic.repository.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.LocalDataSource
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
