package com.smlnskgmail.jaman.randomnotes.model.api.cloud

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

interface CloudDataSource {

    fun saveAllNotes(
        notes: List<Note>,
        result: (success: Boolean) -> Unit
    )

    fun restoreAllNotes(
        afterRestore: (notes: List<Note>, success: Boolean) -> Unit
    )

}
