package com.smlnskgmail.jaman.randomnotes

import com.smlnskgmail.jaman.randomnotes.auth.verification.fake.FakeCloudAuthEmailVerificationTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.fake.FakeCloudAuthPasswordVerificationTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.parse.ParseServerAuthEmailVerificationTest
import com.smlnskgmail.jaman.randomnotes.auth.verification.parse.ParseServerAuthPasswordVerificationTest
import com.smlnskgmail.jaman.randomnotes.entities.EntityWithIdTest
import com.smlnskgmail.jaman.randomnotes.entities.NoteTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    EntityWithIdTest::class,
    NoteTest::class,
    ParseServerAuthEmailVerificationTest::class,
    ParseServerAuthPasswordVerificationTest::class,
    FakeCloudAuthEmailVerificationTest::class,
    FakeCloudAuthPasswordVerificationTest::class
)
class TestSuite
