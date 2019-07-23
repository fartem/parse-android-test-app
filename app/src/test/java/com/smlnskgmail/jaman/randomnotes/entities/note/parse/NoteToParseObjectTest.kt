package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.parse.ParseObject
import com.smlnskgmail.jaman.randomnotes.entities.Note
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertNotEquals
import org.junit.Test
class NoteToParseObjectTest {

    @Test
    fun validateGlobalWrite() {
        val note = Utils.getTestNote()
        checkNoteFields(note)

        val parseNote = note.getParseObject()
        assertNotNull(parseNote)

        checkNoteFromParseFields(parseNote, note)
    }

    @Test
    fun validatePrivateWrite() {
        val note = Utils.getTestNote()
        checkNoteFields(note)

        val parseNote = note.getParseObject()
        assertNotNull(parseNote)

        checkNoteFromParseFields(parseNote, note)
    }

    private fun checkNoteFields(note: Note) {
        assertNotNull(note.parseObjectId)
        assertNotEquals(note.id, -1)
        assertNotNull(note.title)
        assertNotNull(note.subtitle)
    }

    private fun checkNoteFromParseFields(parseObject: ParseObject, note: Note) {
        assertEquals(parseObject.objectId, note.parseObjectId)
        assertEquals(parseObject.getLong(Note.COLUMN_ID), note.id)
        assertEquals(parseObject.getString(Note.COLUMN_TITLE), note.title)
        assertEquals(parseObject.getString(Note.COLUMN_SUBTITLE), note.subtitle)
    }

}