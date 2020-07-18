package com.smlnskgmail.jaman.randomnotes.model

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.model.api.local.LocalDataSource

class DataRepository(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource
) {

    fun allNotes(): MutableList<Note> {
        return localDataSource.allNotes()
    }

    fun saveNote(note: Note) {
        localDataSource.createOrUpdateNote(note)
    }

    fun syncNotes(
        errorOnSave: (success: Boolean) -> Unit
    ) {
        val notes = localDataSource.allNotes()
        cloudDataSource.saveAllNotes(notes) { success ->
            if (success) {
                notes.forEach { note ->
                    localDataSource.createOrUpdateNote(
                        note
                    )
                }
            } else {
                errorOnSave(success)
            }
        }
    }

    fun destroy() {
        localDataSource.destroy()
    }

    fun restoreAllNotes(
        afterRestore: (success: Boolean) -> Unit
    ) {
        val localNotes = localDataSource.allNotes()
        cloudDataSource.restoreAllNotes { notes, success ->
            if (success) {
                notes.forEach { note ->
                    val localNote = localNotes.firstOrNull {
                        it.remoteId == note.remoteId
                    }
                    if (localNote != null) {
                        note.id = localNote.id
                    }
                    localDataSource.createOrUpdateNote(
                        note
                    )
                }
            }
            afterRestore(success)
        }
    }

    fun delete(note: Note) {
        localDataSource.delete(note)
    }

}
