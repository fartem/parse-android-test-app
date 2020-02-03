package com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note

interface CloudDataSource {

    fun saveAllNotes(
        notes: List<Note>,
        errorOnSave: (e: Exception?) -> Unit
    )

    fun restoreAllNotes(
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    )

}
