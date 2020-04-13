package com.smlnskgmail.jaman.randomnotes.entities

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class NoteTest {

    @Test
    fun validateFields() {
        val title = "First note"
        val subtitle = "None"
        val remoteId = "309wds1"
        val positionInList = 1

        val note = Note(
            title,
            subtitle,
            remoteId,
            positionInList
        )

        assertEquals(
            title,
            note.title
        )
        assertEquals(
            subtitle,
            note.subtitle
        )
        assertEquals(
            remoteId,
            note.remoteId
        )
        assertEquals(
            positionInList,
            note.positionInList
        )
    }

    @Test
    fun validateEquals() {
        val title = "Title"
        val subtitle = "Subtitle"
        val remoteId = "sak1Olav"
        val positionInList = 1

        val firstNote = Note(
            title,
            subtitle,
            remoteId,
            positionInList
        )
        val secondNote = Note(
            title,
            subtitle,
            remoteId,
            positionInList
        )
        val thirdNote = Note(
            "Third note",
            "Subtitle",
            "q31jkal",
            2
        )

        assertEquals(
            firstNote,
            firstNote
        )
        assertEquals(
            firstNote,
            secondNote
        )
        assertNotEquals(
            firstNote,
            thirdNote
        )
        assertNotEquals(
            secondNote,
            thirdNote
        )

        assertEquals(
            firstNote.hashCode(),
            secondNote.hashCode()
        )
        assertNotEquals(
            firstNote.hashCode(),
            thirdNote.hashCode()
        )
        assertNotEquals(
            secondNote.hashCode(),
            thirdNote.hashCode()
        )

        assertNotEquals(
            firstNote,
            "String"
        )
    }

}
