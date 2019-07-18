package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.smlnskgmail.jaman.randomnotes.entities.Note
import org.junit.Assert
import org.junit.Test

class NoteToParseObjectTest {

    @Test
    fun runTest() {
        val note = Utils.getTestNote()

        Assert.assertNotNull(note.parseObjectId)
        Assert.assertNotEquals(note.id, -1)
        Assert.assertNotNull(note.title)
        Assert.assertNotNull(note.subtitle)

        val parseNote = note.getParseObject()

        Assert.assertNotNull(parseNote)

        Assert.assertEquals(parseNote.objectId, note.parseObjectId)
        Assert.assertEquals(parseNote.getLong(Note.COLUMN_ID), note.id)
        Assert.assertEquals(parseNote.getString(Note.COLUMN_TITLE), note.title)
        Assert.assertEquals(parseNote.getString(Note.COLUMN_SUBTITLE), note.subtitle)
    }

}