package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.parse.ParseObject
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.entities.Note
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

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

        val user = createParseUser("ewurkal1")
        val parseNote = note.getParseObject(false, user)
        assertNotNull(parseNote)

        checkNoteFromParseFields(parseNote, note)
    }

    private fun createParseUser(id: String): ParseUser {
        val user = Mockito.mock(ParseUser::class.java)
        `when`<String>(user.objectId).thenReturn(id)
        `when`<String>(user.username).thenReturn(id)
        return user
    }

    @Test(expected = RuntimeException::class)
    fun validatePrivateWriteErrors() {
        val note = Utils.getTestNote()
        assertNotNull(note)

        val parseNote = note.getParseObject(false, null)
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