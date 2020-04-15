package com.smlnskgmail.jaman.randomnotes.view.list.recycler

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

interface NoteDeleteTarget {

    fun onNoteDelete(note: Note)

}
