package com.smlnskgmail.jaman.randomnotes.note

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.utils.ViewChildClick.withChildId
import com.smlnskgmail.jaman.randomnotes.view.list.recycler.NoteHolder
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDeletionTest : BaseNoteTest() {

    @Test
    fun deleteNote() {
        val note = testNote()

        dataRepository.saveNote(note)
        assertEquals(
            1,
            dataRepository.allNotes().size
        )

        activityTestRule.launchActivity(null)

        onView(withId(R.id.notes_list)).perform(
            actionOnItemAtPosition<NoteHolder>(
                0,
                withChildId(
                    R.id.delete_note
                )
            )
        )
        delay()

        assertEquals(
            0,
            dataRepository.allNotes().size
        )
    }

}
