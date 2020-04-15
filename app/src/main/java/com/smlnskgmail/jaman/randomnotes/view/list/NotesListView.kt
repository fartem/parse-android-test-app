package com.smlnskgmail.jaman.randomnotes.view.list

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

interface NotesListView {

    fun refreshNotesList(
        notes: MutableList<Note>
    )

    fun addNote(note: Note)
    fun deleteNote(note: Note)

    fun showShareSuccess()

    fun showAuthError()
    fun showShareError()
    fun showRestoreError()
    fun showSyncError()

    fun openNoteCreator()
    fun openShareSender()

    fun openAuthPage()

    fun setAuthenticated()
    fun setUnauthenticated()

}
