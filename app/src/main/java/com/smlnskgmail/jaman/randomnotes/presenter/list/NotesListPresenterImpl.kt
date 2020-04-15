package com.smlnskgmail.jaman.randomnotes.presenter.list

import com.smlnskgmail.jaman.randomnotes.model.DataRepository
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.view.list.NotesListView

class NotesListPresenterImpl : NotesListPresenter {

    private lateinit var dataRepository: DataRepository
    private lateinit var cloudAuth: CloudAuth
    private lateinit var notesListView: NotesListView

    private val notes = arrayListOf<Note>()

    override fun init(
        dataRepository: DataRepository,
        cloudAuth: CloudAuth,
        notesListView: NotesListView
    ) {
        this.dataRepository = dataRepository
        this.cloudAuth = cloudAuth
        this.notesListView = notesListView


        notes.addAll(dataRepository.allNotes())
        this.notesListView.refreshNotesList(notes)
        if (this.cloudAuth.isAuthorized()) {
            this.notesListView.setAuthenticated()
        } else {
            this.notesListView.setUnauthenticated()
        }
    }

    override fun shareNotes() {
        notesListView.openShareSender()
    }

    override fun restoreNotes() {
        if (cloudAuth.isAuthorized()) {
            dataRepository.restoreAllNotes {
                if (it == null) {
                    notesListView.refreshNotesList(
                        dataRepository.allNotes()
                    )
                } else {
                    notesListView.showRestoreError()
                }
            }
        } else {
            notesListView.showAuthError()
        }
    }

    override fun syncNotes() {
        if (cloudAuth.isAuthorized()) {
            dataRepository.syncNotes {
                if (it != null) {
                    notesListView.showSyncError()
                }
            }
        } else {
            notesListView.showAuthError()
        }
    }

    override fun createNote() {
        notesListView.openNoteCreator()
    }

    override fun handleCreatedNote(
        note: Note
    ) {
        dataRepository.saveNote(note)
        notes.add(note)
        notesListView.addNote(note)
    }

    override fun handleNoteDeletion(note: Note) {
        dataRepository.delete(note)
        notes.remove(note)
        notesListView.deleteNote(note)
    }

    override fun handleSharedMessage(
        success: Boolean
    ) {
        if (success) {
            notesListView.showShareSuccess()
        } else {
            notesListView.showShareError()
        }
    }

    override fun handleAuthRequest() {
        if (cloudAuth.isAuthorized()) {
            cloudAuth.logOut {
                if (it == null) {
                    notesListView.setUnauthenticated()
                } else {
                    notesListView.showAuthError()
                }
            }
        } else {
            notesListView.openAuthPage()
        }
    }

}
