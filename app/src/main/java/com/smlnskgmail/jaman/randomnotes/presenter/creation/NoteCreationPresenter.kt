package com.smlnskgmail.jaman.randomnotes.presenter.creation

import com.smlnskgmail.jaman.randomnotes.view.creation.NoteCreationView

interface NoteCreationPresenter {

    fun init(
        noteCreationView: NoteCreationView
    )

    fun createNote(
        title: String,
        subtitle: String
    )

}
