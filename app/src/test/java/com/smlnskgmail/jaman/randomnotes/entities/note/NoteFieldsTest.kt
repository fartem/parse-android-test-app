package com.smlnskgmail.jaman.randomnotes.entities.note

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NoteFieldsTest {

    @Test
    fun runTest() {
        val note = Note()

        assertEquals(note.id, -1)
        assertNull(note.title)
        assertNull(note.subtitle)
        assertNull(note.parseObjectId)

        val id = 1L
        val title = "This is a title!"
        val subtitle = "This is a subtitle!"
        val parseObjectId = "glvdrut39"

        note.id = id
        note.title = title
        note.subtitle = subtitle
        note.parseObjectId = parseObjectId

        assertEquals(note.id, id)
        assertEquals(note.title, title)
        assertEquals(note.subtitle, subtitle)
        assertEquals(note.parseObjectId, parseObjectId)
    }

}