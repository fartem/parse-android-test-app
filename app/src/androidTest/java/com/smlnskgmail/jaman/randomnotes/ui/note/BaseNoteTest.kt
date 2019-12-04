package com.smlnskgmail.jaman.randomnotes.ui.note

import androidx.test.rule.ActivityTestRule
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note
import org.junit.Rule
import org.junit.Test

abstract class BaseNoteTest {

    private val note = Note("Test note (This is title)", "Test note (This is subtitle)")

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    fun getActivity(): MainActivity = activityRule.activity

    @Test
    abstract fun runTest()

    fun note() = note

    fun delay(additional: Int = 1) {
        Thread.sleep(500L * additional)
    }

}