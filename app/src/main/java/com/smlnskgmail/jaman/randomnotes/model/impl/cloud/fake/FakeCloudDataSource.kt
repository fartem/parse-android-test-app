package com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.utils.OpenForTests

@OpenForTests
class FakeCloudDataSource : CloudDataSource {

    private val storage = mutableListOf<Note>()

    override fun saveAllNotes(
        notes: List<Note>,
        result: (success: Boolean) -> Unit
    ) {
        storage.addAll(notes)
        result(true)
    }

    override fun restoreAllNotes(
        afterRestore: (notes: List<Note>, success: Boolean) -> Unit
    ) {
        afterRestore(
            emptyList(),
            true
        )
    }

}
