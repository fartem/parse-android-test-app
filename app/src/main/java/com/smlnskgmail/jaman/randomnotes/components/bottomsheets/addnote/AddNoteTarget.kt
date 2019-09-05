package com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote

import com.smlnskgmail.jaman.randomnotes.entities.note.Note

interface AddNoteTarget {

    fun newNoteAdded(note: Note)

}