package com.smlnskgmail.jaman.randomnotes

import com.smlnskgmail.jaman.randomnotes.entities.EntityFieldsTest
import com.smlnskgmail.jaman.randomnotes.entities.note.NoteFieldsTest
import com.smlnskgmail.jaman.randomnotes.entities.note.parse.NoteFromParseObjectTest
import com.smlnskgmail.jaman.randomnotes.entities.note.parse.NoteToParseObjectTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EntityFieldsTest::class,
    NoteFieldsTest::class,
    NoteToParseObjectTest::class,
    NoteFromParseObjectTest::class
)
class TestSuite