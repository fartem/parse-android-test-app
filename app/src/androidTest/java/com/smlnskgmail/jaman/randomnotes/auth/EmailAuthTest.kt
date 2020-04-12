package com.smlnskgmail.jaman.randomnotes.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.logic.login.LoginFragment
import com.smlnskgmail.jaman.randomnotes.logic.main.MainFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailAuthTest : BaseAuthTest() {

    @Test
    fun signUpWithEmail() {
        whenever(cloudAuth.isValidEmail(any())).thenReturn(true)
        whenever(cloudAuth.isValidPassword(any())).thenReturn(true)
        whenever(cloudAuth.signUpWithEmail(any(), any(), any(), any())).thenAnswer {
            val callback = it.getArgument(3) as ((e: Exception?) -> Unit)
            callback.invoke(
                null
            )
        }

        activityTestRule.launchActivity(null)
        delay()

        openAuthScreen()

        onView(withId(R.id.register_account)).perform(click())
        delay()

        runAuthActions()
        assertAuthData()
    }

    private fun runAuthActions() {
        onView(withId(R.id.email)).perform(
            replaceText("artem.fomchenkov@outlook.com"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.password)).perform(
            replaceText("qwerty12345"),
            closeSoftKeyboard()
        )
        delay()

        onView(withId(R.id.login_action)).perform(click())
        delay()
    }

    private fun assertAuthData() {
        val fragmentManager = activityTestRule.activity.supportFragmentManager
        assertEquals(
            1,
            fragmentManager.fragments.size
        )
        assertEquals(
            MainFragment::class.java.simpleName,
            fragmentManager.fragments[0].javaClass.simpleName
        )
    }

    @Test
    fun logInWithEmail() {
        whenever(cloudAuth.isValidEmail(any())).thenReturn(true)
        whenever(cloudAuth.isValidPassword(any())).thenReturn(true)
        whenever(cloudAuth.signInWithEmail(any(), any(), any())).thenAnswer {
            val callback = it.getArgument(2) as ((e: Exception?) -> Unit)
            callback.invoke(
                null
            )
        }

        activityTestRule.launchActivity(null)
        delay()

        openAuthScreen()
        runAuthActions()
        assertAuthData()
    }

    @Test
    fun authWithError() {
        whenever(cloudAuth.isValidEmail(any())).thenReturn(true)
        whenever(cloudAuth.isValidPassword(any())).thenReturn(true)
        whenever(cloudAuth.signInWithEmail(any(), any(), any())).thenAnswer {
            val callback = it.getArgument(2) as ((e: Exception?) -> Unit)
            callback.invoke(
                RuntimeException(
                    "Runtime Exception!"
                )
            )
        }

        activityTestRule.launchActivity(null)
        delay()

        openAuthScreen()
        runAuthActions()

        val fragmentManager = activityTestRule.activity.supportFragmentManager
        assertEquals(
            2,
            fragmentManager.fragments.size
        )
        assertEquals(
            LoginFragment::class.java.simpleName,
            fragmentManager.fragments[1].javaClass.simpleName
        )
    }

}
