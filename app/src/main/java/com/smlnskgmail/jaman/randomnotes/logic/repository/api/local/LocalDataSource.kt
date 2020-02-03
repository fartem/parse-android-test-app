package com.smlnskgmail.jaman.randomnotes.logic.repository.api.local

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note

interface LocalDataSource {

    fun allNotes(): List<Note>

    fun createOrUpdateNote(note: Note)
    fun createNotes(notes: List<Note>)

    fun delete(note: Note)

    fun destroy()

}
