package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.smlnskgmail.jaman.randomnotes.entities.note.Note

object Utils {

    fun getTestNote(): Note {
        val note = Note()
        note.parseObjectId = "whweAWFaw1"
        note.id = 1
        note.title = "Title"
        note.subtitle = "Subtitle"
        return note
    }

}