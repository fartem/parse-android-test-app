package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.smlnskgmail.jaman.randomnotes.navigation.BaseFragment
import com.smlnskgmail.jaman.randomnotes.navigation.FragmentResume
import com.smlnskgmail.jaman.randomnotes.navigation.auth.LoginFragment
import com.smlnskgmail.jaman.randomnotes.navigation.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val fragmentBackStackListener = FragmentManager.OnBackStackChangedListener {
        val currentFragment = getCurrentFragment()
        if (currentFragment is FragmentResume) {
            currentFragment.onFragmentResume()
        }
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(fragmentBackStackListener)
        showBaseFragment(MainFragment(), false)
    }

    fun loginComplete() {
        showMainFragment()
    }

    fun loginError() {
        showMainFragment()
    }

    private fun showMainFragment() {
        val mainFragment: MainFragment = (supportFragmentManager
            .findFragmentByTag(MainFragment::class.java.name) ?: MainFragment()) as MainFragment
        showBaseFragment(mainFragment)
    }

    fun showLoginFragment() {
        showBaseFragment(LoginFragment())
    }

    private fun showBaseFragment(baseFragment: BaseFragment, inBackStack: Boolean= true) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, baseFragment)
        if (inBackStack) {
            fragmentTransaction.addToBackStack(baseFragment::class.java.name)
        }
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(fragmentBackStackListener)
    }

}
