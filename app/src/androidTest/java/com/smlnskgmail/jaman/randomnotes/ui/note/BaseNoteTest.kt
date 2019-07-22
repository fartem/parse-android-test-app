package com.smlnskgmail.jaman.randomnotes.ui.note

import androidx.test.rule.ActivityTestRule
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.entities.Note
import org.junit.Rule
import org.junit.Test

abstract class BaseNoteTest {

    private val testNote = Note("Test note (This is title)", "Test note (This is subtitle)")

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    fun getActivity() = activityRule.activity

    @Test
    abstract fun runTest()


    fun getTestNote() = testNote

    fun delay(additional: Int = 1) {
        Thread.sleep(500L * additional)
    }

}