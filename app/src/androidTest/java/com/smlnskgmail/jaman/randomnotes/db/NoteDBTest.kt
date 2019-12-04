package com.smlnskgmail.jaman.randomnotes.db

import com.smlnskgmail.jaman.randomnotes.repository.DataRepositoryAccessor
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note
import org.junit.Assert.*
import org.junit.Test

class NoteDBTest {

    @Test
    fun runTest() {
        val note = Note("Clean aunt's PC", "Weekend")
        assertEquals(note.id, -1)

        DataRepositoryAccessor.get().saveNote(note)

        assertTrue(note.id > 0)
        assertTrue(DataRepositoryAccessor.get().allNotes().contains(note))

        DataRepositoryAccessor.get().delete(note)
        assertFalse(DataRepositoryAccessor.get().allNotes().contains(note))
    }

}