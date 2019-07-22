package com.smlnskgmail.jaman.randomnotes.ui.note

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.smlnskgmail.jaman.randomnotes.R
import org.junit.Test

class NoteCreationTest : BaseNoteTest() {

    @Test
    override fun runTest() {
        createNewNote()
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

}