package com.smlnskgmail.jaman.randomnotes.logic.repository

import com.smlnskgmail.jaman.randomnotes.logic.repository.entities.Note

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

    fun saveNotes(notes: List<Note>) {
        localDataSource.createNotes(notes)
    }

    fun syncNotes(notes: List<Note>, errorOnSave: (e: Exception) -> Unit) {
        cloudDataSource.saveAllNotes(notes, errorOnSave)
    }

    fun destroy() {
        localDataSource.destroy()
    }

    fun restoreAllNotes(
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    ) {
        cloudDataSource.restoreAllNotes(notes, afterRestore)
    }

    fun delete(note: Note) {
        localDataSource.delete(note)
    }

}
