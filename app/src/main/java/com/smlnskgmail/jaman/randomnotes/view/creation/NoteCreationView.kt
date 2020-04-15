package com.smlnskgmail.jaman.randomnotes.view.creation

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

interface NoteCreationView {

    fun noteCreated(
        note: Note
    )

}
