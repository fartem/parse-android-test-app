package com.smlnskgmail.jaman.randomnotes.db

import com.smlnskgmail.jaman.randomnotes.entities.note.Note
import com.smlnskgmail.jaman.randomnotes.entities.note.support.NoteFactory
import org.junit.Assert.*
import org.junit.Test

class NoteDBTest {

    @Test
    fun runTest() {
        val note = Note("Clean aunt's PC", "Weekend")
        assertEquals(note.id, -1)

        NoteFactory.save(note)

        assertTrue(note.id > 0)
        assertTrue(NoteFactory.all().contains(note))

        NoteFactory.delete(note)
        assertFalse(NoteFactory.all().contains(note))
    }

}