package com.smlnskgmail.jaman.randomnotes.logic.repository

import com.smlnskgmail.jaman.randomnotes.logic.repository.entities.Note

interface CloudDataSource {

    fun saveAllNotes(
        notes: List<Note>,
        errorOnSave: (e: Exception) -> Unit
    )

    fun restoreAllNotes(
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    )

}
