package com.smlnskgmail.jaman.randomnotes.ui.note

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesHolder
import com.smlnskgmail.jaman.randomnotes.entities.Note
import com.smlnskgmail.jaman.randomnotes.ui.utils.ChildClick
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteCreationTest : BaseNoteTest() {

    private lateinit var notes: List<Note>

    private var testNotePosition: Int = 0

    @Test
    override fun runTest() {
        createNewNote()
        setIndex()
        deleteNote()
        checkNoteInDB()
    }

    private fun createNewNote() {
        onView(withId(R.id.main_fab_menu)).perform(click())
        delay()

        onView(withText(getActivity().getString(R.string.action_add_note)))
            .perform(click())
        delay()

        onView(withId(R.id.edit_title)).perform(typeText(getTestNote().title),
            closeSoftKeyboard())
        delay()
        onView(withId(R.id.edit_subtitle)).perform(typeText(getTestNote().subtitle),
            closeSoftKeyboard())
        delay()

        onView(withId(R.id.save_note)).perform(click())
        delay()
    }

    private fun setIndex() {
        testNotePosition = Note.getAllNotes().indexOfLast {
            it.contentEquals(getTestNote())
        }
        assertTrue(testNotePosition != -1)
    }

    private fun deleteNote() {
        onView(withId(R.id.notes))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<NotesHolder>(
                    testNotePosition,
                    ChildClick.withChildId(R.id.delete_note)
                )
            )
    }

    private fun checkNoteInDB() {
        val testNote = getTestNote()

        Assert.assertTrue(Note.getAllNotes().none {
            it.contentEquals(testNote)
        })
    }

}