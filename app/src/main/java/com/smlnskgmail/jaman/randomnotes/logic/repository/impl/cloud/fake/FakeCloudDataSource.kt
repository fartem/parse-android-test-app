package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note

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
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    ) {
        afterRestore(notes, null)
    }

}
