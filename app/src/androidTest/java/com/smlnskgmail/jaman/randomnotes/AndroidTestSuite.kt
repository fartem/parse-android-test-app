package com.smlnskgmail.jaman.randomnotes

import com.smlnskgmail.jaman.randomnotes.auth.EmailAuthTest
import com.smlnskgmail.jaman.randomnotes.auth.FacebookAuthTest
import com.smlnskgmail.jaman.randomnotes.auth.GoogleAuthTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.fake.FakeCloudAuthEmailVerificationTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.fake.FakeCloudAuthPasswordVerificationTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.parse.ParseServerAuthEmailVerificationTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.parse.ParseServerAuthPasswordVerificationTest
import com.smlnskgmail.jaman.randomnotes.invite.InviteTest
import com.smlnskgmail.jaman.randomnotes.note.NoteCreationTest
import com.smlnskgmail.jaman.randomnotes.note.NoteDeletionTest
import com.smlnskgmail.jaman.randomnotes.note.NotesRestoreTest
import com.smlnskgmail.jaman.randomnotes.note.NotesSyncTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    NoteCreationTest::class,
    NoteDeletionTest::class,
    NotesSyncTest::class,
    NotesRestoreTest::class,
    ParseServerAuthEmailVerificationTest::class,
    ParseServerAuthPasswordVerificationTest::class,
    FakeCloudAuthEmailVerificationTest::class,
    FakeCloudAuthPasswordVerificationTest::class,
    EmailAuthTest::class,
    GoogleAuthTest::class,
    FacebookAuthTest::class,
    InviteTest::class
)
class AndroidTestSuite
