package com.smlnskgmail.jaman.randomnotes.invite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerTestAppComponent
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudInviteModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudDataSource
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudInvite
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.local.LocalDataSource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class InviteTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var cloudDataSource: CloudDataSource

    @Mock
    lateinit var cloudAuth: CloudAuth

    @Mock
    lateinit var cloudInvite: CloudInvite

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val testAppComponent = DaggerTestAppComponent.builder()
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
            .cloudInviteModule(
                CloudInviteModule(
                    cloudInvite
                )
            )
            .build()
        testAppComponent.inject(this)

        val app = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as App
        app.appComponent = testAppComponent

        whenever(cloudAuth.isAuthorized()).thenReturn(true)
        whenever(cloudInvite.invite(any(), any())).thenAnswer {
            val callback = it.getArgument(1) as ((e: Exception?) -> Unit)
            callback.invoke(
                null
            )
        }
    }

    @Test
    fun invite() {
        activityTestRule.launchActivity(null)

        onView(withId(R.id.main_fab_menu)).perform(click())
        delay()

        onView(withId(R.id.share_access)).perform(click())
        onView(withId(R.id.share_access)).perform(click())
        delay()

        onView(withId(R.id.dialog_invite_email)).perform(
            replaceText("artem.fomchenkov@outlook.com"),
            closeSoftKeyboard()
        )
        delay()

        onView(withId(R.id.dialog_invite_request)).perform(click())
    }

    private fun delay() {
        Thread.sleep(1_000)
    }

}
