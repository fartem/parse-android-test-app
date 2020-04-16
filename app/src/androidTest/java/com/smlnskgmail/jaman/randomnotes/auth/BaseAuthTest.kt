package com.smlnskgmail.jaman.randomnotes.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.di.components.DaggerTestAppComponent
import com.smlnskgmail.jaman.randomnotes.di.components.TestAppComponent
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudAuthModule
import com.smlnskgmail.jaman.randomnotes.di.modules.CloudInviteModule
import com.smlnskgmail.jaman.randomnotes.di.modules.DataRepositoryModule
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudInvite
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake.FakeCloudAuth
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake.FakeCloudDataSource
import com.smlnskgmail.jaman.randomnotes.model.impl.ormlite.OrmLiteDataSource
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

open class BaseAuthTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    lateinit var testAppComponent: TestAppComponent

    @Mock
    lateinit var localDataSource: OrmLiteDataSource

    @Mock
    lateinit var cloudDataSource: FakeCloudDataSource

    @Mock
    lateinit var cloudAuth: FakeCloudAuth

    @Mock
    lateinit var cloudInvite: CloudInvite

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

        whenever(cloudAuth.isAuthorized()).thenReturn(false)
        whenever(cloudAuth.logOut(any())).thenAnswer {
            val callback = it.getArgument(0) as ((e: Exception?) -> Unit)
            callback.invoke(
                null
            )
        }
    }

    fun openAuthScreen() {
        try {
            onView(withId(R.id.menu_auth_action)).perform(click())
        } catch (e: NoMatchingViewException) {
            openActionBarOverflowOrOptionsMenu(
                activityTestRule.activity
            )
            onView(withId(R.id.menu_auth_action)).perform(click())
        }
        delay()
    }

    fun delay() {
        Thread.sleep(1_000)
    }

}
