package com.smlnskgmail.jaman.randomnotes

import com.smlnskgmail.jaman.randomnotes.entities.EntityWithIdTest
import com.smlnskgmail.jaman.randomnotes.entities.NoteTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EntityWithIdTest::class,
    NoteTest::class
)
class TestSuite
