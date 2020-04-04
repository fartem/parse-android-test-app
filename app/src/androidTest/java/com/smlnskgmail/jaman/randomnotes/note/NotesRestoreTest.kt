package com.smlnskgmail.jaman.randomnotes.note

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.smlnskgmail.jaman.randomnotes.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesRestoreTest : BaseNoteTest() {

    @Test
    fun restoreNotes() {
        assertEquals(
            0,
            dataRepository.allNotes().size
        )

        activityTestRule.launchActivity(null)

        onView(withId(R.id.main_fab_menu)).perform(click())
        delay()

        onView(withId(R.id.restore_notes)).perform(click())
        onView(withId(R.id.restore_notes)).perform(click())
        delay()

        assertEquals(
            1,
            dataRepository.allNotes().size
        )
        assertEquals(
            dataRepository.allNotes().size,
            (activityTestRule.activity.findViewById(
                R.id.notes_list
            ) as RecyclerView).adapter!!.itemCount
        )
    }

}
