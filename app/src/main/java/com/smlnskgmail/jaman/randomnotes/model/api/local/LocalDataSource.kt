package com.smlnskgmail.jaman.randomnotes.model.api.local

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

interface LocalDataSource {

    fun allNotes(): MutableList<Note>

    fun createOrUpdateNote(note: Note)
    fun createNotes(notes: List<Note>)

    fun delete(note: Note)

    fun destroy()

}
