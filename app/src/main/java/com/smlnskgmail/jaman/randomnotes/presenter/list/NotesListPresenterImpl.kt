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
    }

    override fun shareNotes() {
        notesListView.openShareSender()
    }

    override fun restoreNotes() {
        if (cloudAuth.isAuthorized()) {
            dataRepository.restoreAllNotes { success ->
                if (success) {
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
            dataRepository.syncNotes { success ->
                if (success) {
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

    override fun authChanged() {
        if (cloudAuth.isAuthorized()) {
            notesListView.setAuthenticated()
        } else {
            notesListView.setUnauthenticated()
        }
    }

    override fun handleAuthRequest() {
        if (cloudAuth.isAuthorized()) {
            cloudAuth.logOut { success ->
                if (success) {
                    notesListView.setUnauthenticated()
                } else {
                    notesListView.showAuthError()
                }
            }
        } else {
            notesListView.openAuthPage()
        }
    }

    override fun deleteAccount() {
        cloudAuth.deleteAccount { success ->
            if (success) {
                notesListView.setUnauthenticated()
            } else {
                notesListView.showAuthError()
            }
        }
    }

}
