package com.smlnskgmail.jaman.randomnotes.entities

import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class NoteTest : BaseEntityTest() {

    private val title = "First note"
    private val subtitle = "None"
    private val remoteId = "309wds1"

    private val note = Note(
        title,
        subtitle,
        remoteId
    )

    override fun `Validate fields`() {
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
    }

    override fun `Validate equals()`() {
        assertEquals(
            note,
            note
        )
        assertEquals(
            Note(
                title,
                subtitle,
                remoteId
            ),
            note
        )

        assertNotEquals(
            Note(
                "Another title",
                subtitle,
                remoteId
            ),
            note
        )
        assertNotEquals(
            Note(
                title,
                "Another subtitle",
                remoteId
            ),
            note
        )
        assertNotEquals(
            Note(
                title,
                subtitle,
                "e5j1iv"
            ),
            note
        )
        assertNotEquals(
            note,
            null
        )
        assertNotEquals(
            note,
            "String"
        )
    }

    override fun `Validate hashCode()`() {
        assertEquals(
            note.hashCode(),
            note.hashCode()
        )
        assertEquals(
            Note(
                title,
                subtitle,
                remoteId
            ).hashCode(),
            note.hashCode()
        )

        assertNotEquals(
            Note(
                "Another title",
                subtitle,
                remoteId
            ).hashCode(),
            note.hashCode()
        )
    }

}
