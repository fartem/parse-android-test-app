package com.smlnskgmail.jaman.randomnotes.db

import com.smlnskgmail.jaman.randomnotes.entities.Note
import org.junit.Assert.*
import org.junit.Test

class NoteDBTest {

    @Test
    fun runTest() {
        val testNote = Note("Clean aunt's PC", "Weekend")
        assertEquals(testNote.id, -1)

        testNote.save()

        assertTrue(testNote.id > 0)
        assertTrue(Note.getAllNotes().contains(testNote))

        testNote.delete()
        assertFalse(Note.getAllNotes().contains(testNote))
    }

}