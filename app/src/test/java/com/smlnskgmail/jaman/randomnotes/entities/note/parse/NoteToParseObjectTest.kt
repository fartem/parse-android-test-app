package com.smlnskgmail.jaman.randomnotes.entities.note.parse

import com.parse.ParseObject
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.entities.Note
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class NoteToParseObjectTest {

    @Test
    fun validateGlobalWrite() {
        val note = Utils.getTestNote()
        checkNoteFields(note)

        val parseNote = note.getParseObject()
        Assert.assertNotNull(parseNote)

        checkNoteFromParseFields(parseNote, note)
    }

    @Test
    fun validatePrivateWrite() {
        val note = Utils.getTestNote()
        checkNoteFields(note)

        val user = createParseUser("ewurkal1")
        val parseNote = note.getParseObject(false, user)
        Assert.assertNotNull(parseNote)

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
        Assert.assertNotNull(note)

        val parseNote = note.getParseObject(false, null)
    }

    private fun checkNoteFields(note: Note) {
        Assert.assertNotNull(note.parseObjectId)
        Assert.assertNotEquals(note.id, -1)
        Assert.assertNotNull(note.title)
        Assert.assertNotNull(note.subtitle)
    }

    private fun checkNoteFromParseFields(parseObject: ParseObject, note: Note) {
        Assert.assertEquals(parseObject.objectId, note.parseObjectId)
        Assert.assertEquals(parseObject.getLong(Note.COLUMN_ID), note.id)
        Assert.assertEquals(parseObject.getString(Note.COLUMN_TITLE), note.title)
        Assert.assertEquals(parseObject.getString(Note.COLUMN_SUBTITLE), note.subtitle)
    }

}