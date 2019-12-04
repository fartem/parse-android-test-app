package com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote

import com.smlnskgmail.jaman.randomnotes.repository.entities.Note

interface AddNoteTarget {

    fun newNoteAdded(note: Note)

}