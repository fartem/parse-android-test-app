package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.parse.ParseObject
import com.smlnskgmail.jaman.randomnotes.entities.Note
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertNotEquals
import org.junit.Test

class NoteFromParseObjectTest {

    @Test
    fun runTest() {
        val note = Utils.getTestNote()

        assertNotNull(note.parseObjectId)
        assertNotEquals(note.id, -1)
        assertNotNull(note.title)
        assertNotNull(note.subtitle)

        val parseNote = ParseObject(Note.TABLE_NOTE)
        parseNote.objectId = note.parseObjectId
        parseNote.put(Note.COLUMN_ID, note.id)
        parseNote.put(Note.COLUMN_TITLE, note.title!!)
        parseNote.put(Note.COLUMN_SUBTITLE, note.subtitle!!)

        val noteFromParseObject = Note()
        noteFromParseObject.restoreFromParseObject(parseNote)

        assertEquals(note.parseObjectId, noteFromParseObject.parseObjectId)
        assertEquals(noteFromParseObject.id, -1)
        assertEquals(note.title, noteFromParseObject.title)
        assertEquals(note.subtitle, noteFromParseObject.subtitle)
    }

}