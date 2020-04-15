package com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.utils.OpenForTests

@OpenForTests
class FakeCloudDataSource : CloudDataSource {

    private val storage = mutableListOf<Note>()

    override fun saveAllNotes(
        notes: List<Note>,
        errorOnSave: (e: Exception?) -> Unit
    ) {
        storage.addAll(notes)
        errorOnSave(null)
    }

    override fun restoreAllNotes(
        afterRestore: (notes: List<Note>, e: Exception?) -> Unit
    ) {
        afterRestore(
            emptyList(),
            null
        )
    }

}
