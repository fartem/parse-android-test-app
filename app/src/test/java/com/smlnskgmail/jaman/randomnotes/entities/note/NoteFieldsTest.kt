package com.smlnskgmail.jaman.randomnotes.entities.note

import com.smlnskgmail.jaman.randomnotes.entities.Note
import org.junit.Assert
import org.junit.Test

class NoteFieldsTest {

    @Test
    fun runTest() {
        val note = Note()

        Assert.assertEquals(note.id, -1)
        Assert.assertNull(note.title)
        Assert.assertNull(note.subtitle)
        Assert.assertNull(note.parseObjectId)

        val id = 1L
        val title = "This is a title!"
        val subtitle = "This is a subtitle!"
        val parseObjectId = "glvdrut39"

        note.id = id
        note.title = title
        note.subtitle = subtitle
        note.parseObjectId = parseObjectId

        Assert.assertEquals(note.id, id)
        Assert.assertEquals(note.title, title)
        Assert.assertEquals(note.subtitle, subtitle)
        Assert.assertEquals(note.parseObjectId, parseObjectId)
    }

}