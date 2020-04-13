package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smlnskgmail.jaman.randomnotes.components.fragments.BaseFragment
import com.smlnskgmail.jaman.randomnotes.components.views.LongSnackbar
import com.smlnskgmail.jaman.randomnotes.logic.login.LoginFragment
import com.smlnskgmail.jaman.randomnotes.logic.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMainFragment()
    }

    private fun showMainFragment() {
        showBaseFragment(
            MainFragment(),
            true
        )
    }

    fun loginComplete() {
        showMainFragment()
    }

    fun loginError() {
        LongSnackbar(
            findViewById(android.R.id.content),
            getString(R.string.error_auth)
        ).show()
    }

    fun showLoginFragment() {
        showBaseFragment(
            LoginFragment()
        )
    }

    private fun showBaseFragment(
        baseFragment: BaseFragment,
        replace: Boolean = false
    ) {
        supportFragmentManager.beginTransaction().let {
            if (replace) {
                supportFragmentManager.popBackStack()
                it.replace(
                    R.id.container,
                    baseFragment,
                    baseFragment::class.java.name
                )
            } else {
                it.add(
                    R.id.container,
                    baseFragment,
                    baseFragment::class.java.name
                )
                it.addToBackStack(null)
            }
            it.commit()
        }
    }

}
