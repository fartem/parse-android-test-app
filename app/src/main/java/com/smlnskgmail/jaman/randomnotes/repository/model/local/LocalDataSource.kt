package com.smlnskgmail.jaman.randomnotes.repository.model.local

import com.smlnskgmail.jaman.randomnotes.repository.entities.Note

interface LocalDataSource {

    fun allNotes(): List<Note>

    fun createOrUpdateNote(note: Note)
    fun createNotes(notes: List<Note>)

    fun delete(note: Note)

    fun destroy()

}
