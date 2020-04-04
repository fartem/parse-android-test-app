package com.smlnskgmail.jaman.randomnotes.note

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerTestAppComponent
import com.smlnskgmail.jaman.randomnotes.di.components.TestAppComponent
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake.FakeCloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake.FakeCloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.local.ormlite.OrmLiteDataSource
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import javax.inject.Inject

open class BaseNoteTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    lateinit var testAppComponent: TestAppComponent

    @Inject
    lateinit var dataRepository: DataRepository

    @Mock
    lateinit var localDataSource: OrmLiteDataSource

    @Mock
    lateinit var cloudDataSource: FakeCloudDataSource

    @Mock
    lateinit var cloudAuth: FakeCloudAuth

    private val localTestNotes = arrayListOf<Note>()
    private val cloudTestNotes = arrayListOf<Note>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testAppComponent = DaggerTestAppComponent.builder()
            .dataRepositoryModule(
                DataRepositoryModule(
                    localDataSource,
                    cloudDataSource
                )
            )
            .cloudAuthModule(
                CloudAuthModule(
                    cloudAuth
                )
            )
            .build()
        testAppComponent.inject(this)

        val app = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as App
        app.appComponent = testAppComponent

        whenever(localDataSource.allNotes()).thenReturn(localTestNotes)
        whenever(localDataSource.createOrUpdateNote(any())).then {
            localTestNotes.add(
                testNote()
            )
        }
        whenever(localDataSource.delete(any())).then {
            localTestNotes.remove(
                testNote()
            )
        }
        whenever(cloudDataSource.saveAllNotes(any(), any())).then {
            cloudTestNotes.add(
                testNote()
            )
        }
        whenever(cloudDataSource.restoreAllNotes(any())).thenAnswer {
            val callback = it.getArgument(0) as ((notes: List<Note>, e: Exception?) -> Unit)
            cloudTestNotes.add(
                testNote()
            )
            callback.invoke(
                cloudTestNotes,
                null
            )
        }
        whenever(cloudAuth.isAuthorized()).thenReturn(true)
    }

    fun testNote(): Note {
        return Note(
            "Title",
            "Subtitle"
        )
    }

    fun delay() {
        Thread.sleep(1_000)
    }

}
