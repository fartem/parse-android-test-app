package com.smlnskgmail.jaman.randomnotes

import com.smlnskgmail.jaman.randomnotes.db.NoteDBTest
import com.smlnskgmail.jaman.randomnotes.ui.note.NoteCreationTest
import com.smlnskgmail.jaman.randomnotes.ui.note.NoteDeletionTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    NoteDBTest::class,
    NoteCreationTest::class,
    NoteDeletionTest::class
)
class AndroidTestSuite