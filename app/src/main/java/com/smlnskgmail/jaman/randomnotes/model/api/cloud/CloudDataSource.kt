package com.smlnskgmail.jaman.randomnotes.model.api.cloud

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

interface CloudDataSource {

    fun saveAllNotes(
        notes: List<Note>,
        errorOnSave: (e: Exception?) -> Unit
    )

    fun restoreAllNotes(
        afterRestore: (notes: List<Note>, e: Exception?) -> Unit
    )

}
