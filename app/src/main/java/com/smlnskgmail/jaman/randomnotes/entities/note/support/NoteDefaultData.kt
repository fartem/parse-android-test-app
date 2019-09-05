package com.smlnskgmail.jaman.randomnotes.entities.note.support

import android.content.Context
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.entities.note.Note

class NoteDefaultData(private val context: Context) {

    fun create(){
        val note = Note()
        note.title = context.getString(R.string.default_note_title)
        note.subtitle = context.getString(R.string.default_note_subtitle)
        NoteFactory.save(note)
    }

}