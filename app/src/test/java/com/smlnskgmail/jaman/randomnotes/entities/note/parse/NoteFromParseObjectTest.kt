package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.parse.ParseObject
import com.smlnskgmail.jaman.randomnotes.entities.Note
import org.junit.Assert
import org.junit.Test

class NoteFromParseObjectTest {

    @Test
    fun runTest() {
        val note = Utils.getTestNote()

        Assert.assertNotNull(note.parseObjectId)
        Assert.assertNotEquals(note.id, -1)
        Assert.assertNotNull(note.title)
        Assert.assertNotNull(note.subtitle)

        val parseNote = ParseObject(Note.TABLE_NOTE)
        parseNote.objectId = note.parseObjectId
        parseNote.put(Note.COLUMN_ID, note.id)
        parseNote.put(Note.COLUMN_TITLE, note.title!!)
        parseNote.put(Note.COLUMN_SUBTITLE, note.subtitle!!)

        val noteFromParseObject = Note()
        noteFromParseObject.restoreFromParseObject(parseNote)

        Assert.assertEquals(note.parseObjectId, noteFromParseObject.parseObjectId)
        Assert.assertEquals(noteFromParseObject.id, -1)
        Assert.assertEquals(note.title, noteFromParseObject.title)
        Assert.assertEquals(note.subtitle, noteFromParseObject.subtitle)
    }

}