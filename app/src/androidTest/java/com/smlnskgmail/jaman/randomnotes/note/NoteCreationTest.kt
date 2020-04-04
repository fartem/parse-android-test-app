package com.smlnskgmail.jaman.randomnotes.note

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.smlnskgmail.jaman.randomnotes.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteCreationTest : BaseNoteTest() {

    @Test
    fun createNote() {
        val note = testNote()

        activityTestRule.launchActivity(null)

        onView(withId(R.id.main_fab_menu)).perform(click())
        delay()

        onView(withId(R.id.add_note)).perform(click())
        onView(withId(R.id.add_note)).perform(click())
        delay()

        onView(withId(R.id.edit_title)).perform(
            ViewActions.typeText(note.title),
            ViewActions.closeSoftKeyboard()
        )
        delay()
        onView(withId(R.id.edit_subtitle)).perform(
            ViewActions.typeText(note.subtitle),
            ViewActions.closeSoftKeyboard()
        )
        delay()

        onView(withId(R.id.save_note)).perform(click())
        delay()

        assertEquals(
            dataRepository.allNotes().size,
            (activityTestRule.activity.findViewById(
                R.id.notes_list
            ) as RecyclerView).adapter!!.itemCount
        )
    }

}
