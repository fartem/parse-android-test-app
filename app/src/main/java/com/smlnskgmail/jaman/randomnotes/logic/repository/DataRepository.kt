package com.smlnskgmail.jaman.randomnotes.logic.repository

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.local.LocalDataSource

class DataRepository(
    private val localDataSource: LocalDataSource,
    private val cloudDataSource: CloudDataSource
) {

    fun allNotes(): List<Note> {
        return localDataSource.allNotes()
    }

    fun saveNote(note: Note) {
        localDataSource.createOrUpdateNote(note)
    }

    fun syncNotes(
        errorOnSave: (e: Exception?) -> Unit
    ) {
        val notes = localDataSource.allNotes()
        cloudDataSource.saveAllNotes(notes) {
            if (it == null) {
                notes.forEach { note ->
                    localDataSource.createOrUpdateNote(
                        note
                    )
                }
            } else {
                errorOnSave(it)
            }
        }
    }

    fun destroy() {
        localDataSource.destroy()
    }

    fun restoreAllNotes(
        afterRestore: (e: Exception?) -> Unit
    ) {
        val localNotes = localDataSource.allNotes()
        cloudDataSource.restoreAllNotes() { notes, e ->
            if (e == null) {
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
            afterRestore(e)
        }
    }

    fun delete(note: Note) {
        localDataSource.delete(note)
    }

}
