package com.smlnskgmail.jaman.randomnotes.repository

import com.smlnskgmail.jaman.randomnotes.repository.model.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.repository.model.local.LocalDataSource

object DataRepositoryAccessor {

    private lateinit var dataRepository: DataRepository

    fun initWith(
        localDataSource: LocalDataSource,
        cloudDataSource: CloudDataSource
    ) {
        dataRepository = DataRepository(localDataSource, cloudDataSource)
    }

    fun get(): DataRepository {
        return dataRepository
    }

    fun free() {
        dataRepository.destroy()
    }

}