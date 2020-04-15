package com.smlnskgmail.jaman.randomnotes.presenter.creation

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.view.creation.NoteCreationView

class NoteCreationPresenterImpl : NoteCreationPresenter {

    private lateinit var noteCreationView: NoteCreationView

    override fun init(
        noteCreationView: NoteCreationView
    ) {
        this.noteCreationView = noteCreationView
    }

    override fun createNote(
        title: String,
        subtitle: String
    ) {
        val note = Note(
            title,
            subtitle
        )
        noteCreationView.noteCreated(note)
    }

}
