package com.smlnskgmail.jaman.randomnotes

import com.smlnskgmail.jaman.randomnotes.entities.EntityFieldsTest
import com.smlnskgmail.jaman.randomnotes.entities.note.NoteFieldsTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EntityFieldsTest::class,
    NoteFieldsTest::class
)
class TestSuite