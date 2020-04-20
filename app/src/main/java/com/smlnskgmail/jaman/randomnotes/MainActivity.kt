package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.smlnskgmail.jaman.randomnotes.components.BaseFragment
import com.smlnskgmail.jaman.randomnotes.view.auth.CloudAuthFragment
import com.smlnskgmail.jaman.randomnotes.view.list.NotesListFragment

class MainActivity : AppCompatActivity() {

    private val fragmentsStack = FragmentManager.OnBackStackChangedListener {
        val fragment = supportFragmentManager.fragments[
                supportFragmentManager.backStackEntryCount
        ]
        if (fragment is BaseFragment) {
            fragment.resume()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.fragments.size
            val currentFragment = supportFragmentManager.fragments[count - 1]
            if (currentFragment is BaseFragment) {
                currentFragment.showed()
            }
        }
        supportFragmentManager.addOnBackStackChangedListener(
            fragmentsStack
        )
        showNotesListFragment()
    }

    fun showNotesListFragment() {
        showBaseFragment(
            NotesListFragment(),
            true
        )
    }

    fun showAuthFragment() {
        showBaseFragment(
            CloudAuthFragment()
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
