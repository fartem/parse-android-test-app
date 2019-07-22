package com.smlnskgmail.jaman.randomnotes.ui.note

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesHolder
import com.smlnskgmail.jaman.randomnotes.entities.Note
import com.smlnskgmail.jaman.randomnotes.ui.utils.ChildClick
import org.junit.Assert.assertTrue
import org.junit.Before

class NoteDeletionTest : BaseNoteTest() {

    private lateinit var notes: List<Note>

    private var testNotePosition: Int = 0

    @Before
    fun setIndex() {
        notes = Note.getAllNotes()
        testNotePosition = notes.indexOfLast {
            it.contentEquals(getTestNote())
        }
    }

    override fun runTest() {
        deleteNote()
        delay()

        checkNoteInDB()
    }

    private fun deleteNote() {
        onView(withId(R.id.notes))
            .perform(actionOnItemAtPosition<NotesHolder>(testNotePosition,
                ChildClick.withChildId(R.id.delete_note)))
    }

    private fun checkNoteInDB() {
        val testNote = getTestNote()

        assertTrue(Note.getAllNotes().none {
            it.contentEquals(testNote)
        })
    }

}