package com.smlnskgmail.jaman.randomnotes.logic.notecreation

import com.smlnskgmail.jaman.randomnotes.logic.repository.entities.Note

interface AddNoteTarget {

    fun newNoteAdded(note: Note)

}
