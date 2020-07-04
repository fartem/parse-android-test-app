package com.smlnskgmail.jaman.randomnotes.presenter.list

import com.smlnskgmail.jaman.randomnotes.model.DataRepository
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.view.list.NotesListView

interface NotesListPresenter {

    fun init(
        dataRepository: DataRepository,
        cloudAuth: CloudAuth,
        notesListView: NotesListView
    )

    fun shareNotes()
    fun restoreNotes()
    fun syncNotes()
    fun createNote()

    fun handleCreatedNote(
        note: Note
    )
    fun handleNoteDeletion(
        note: Note
    )
    fun handleSharedMessage(
        success: Boolean
    )

    fun authChanged()
    fun handleAuthRequest()
    fun deleteAccount()

}
