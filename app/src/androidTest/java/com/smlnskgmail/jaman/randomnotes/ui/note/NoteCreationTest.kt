package com.smlnskgmail.jaman.randomnotes.ui.note

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.logic.main.noteslist.NotesHolder
import com.smlnskgmail.jaman.randomnotes.repository.DataRepositoryAccessor
import com.smlnskgmail.jaman.randomnotes.ui.utils.ChildClick
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NoteCreationTest : BaseNoteTest() {

    private var notePosition: Int = 0

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

        onView(withText(getActivity().getString(R.string.action_add_note))).perform(click())
        delay()

        onView(withId(R.id.edit_title)).perform(typeText(note().title), closeSoftKeyboard())
        delay()

        onView(withId(R.id.edit_subtitle)).perform(typeText(note().subtitle), closeSoftKeyboard())
        delay()

        onView(withId(R.id.save_note)).perform(click())
        delay()
    }

    private fun setIndex() {
        notePosition = DataRepositoryAccessor.get().allNotes().indexOfLast {
            it == note()
        }
        assertTrue(notePosition != -1)
    }

    private fun deleteNote() {
        onView(withId(R.id.notes_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<NotesHolder>(notePosition, ChildClick.withChildId(R.id.delete_note)))
    }

    private fun checkNoteInDB() {
        assertFalse(DataRepositoryAccessor.get().allNotes().contains(note()))
    }

}