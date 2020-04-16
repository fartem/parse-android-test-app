package com.smlnskgmail.jaman.randomnotes.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.view.auth.CloudAuthFragment
import com.smlnskgmail.jaman.randomnotes.view.list.NotesListFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoogleAuthTest : BaseAuthTest() {

    @Test
    fun signInWithGoogle() {
        whenever(cloudAuth.signInWithGoogle(any(), any())).thenAnswer {
            val callback = it.getArgument(1) as ((e: Exception?) -> Unit)
            callback.invoke(
                null
            )
        }

        activityTestRule.launchActivity(null)

        openAuthScreen()

        onView(withId(R.id.google_sign_in)).perform(click())
        delay()

        val fragmentManager = activityTestRule.activity.supportFragmentManager
        assertEquals(
            1,
            fragmentManager.fragments.size
        )
        assertEquals(
            NotesListFragment::class.java.simpleName,
            fragmentManager.fragments[0].javaClass.simpleName
        )
    }

    @Test
    fun authWithError() {
        whenever(cloudAuth.signInWithGoogle(any(), any())).thenAnswer {
            val callback = it.getArgument(1) as ((e: Exception?) -> Unit)
            callback.invoke(
                RuntimeException(
                    "Runtime Exception!"
                )
            )
        }

        activityTestRule.launchActivity(null)

        openAuthScreen()

        onView(withId(R.id.google_sign_in)).perform(click())
        delay()

        val fragmentManager = activityTestRule.activity.supportFragmentManager
        assertEquals(
            2,
            fragmentManager.fragments.size
        )
        assertEquals(
            CloudAuthFragment::class.java.simpleName,
            fragmentManager.fragments[1].javaClass.simpleName
        )
    }

}
