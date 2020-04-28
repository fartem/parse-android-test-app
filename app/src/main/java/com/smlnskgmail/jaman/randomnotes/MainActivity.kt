package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.smlnskgmail.jaman.randomnotes.components.BaseFragment
import com.smlnskgmail.jaman.randomnotes.view.auth.CloudAuthFragment
import com.smlnskgmail.jaman.randomnotes.view.list.NotesListFragment

class MainActivity : AppCompatActivity() {

    private val fragmentsStack = FragmentManager.OnBackStackChangedListener {
        val fragment = supportFragmentManager.fragments.last()
        if (fragment is BaseFragment) {
            fragment.resume()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(
            fragmentsStack
        )
        showBaseFragment(
            NotesListFragment()
        )
    }

    fun authComplete() {
        onBackPressed()
    }

    fun showAuthFragment() {
        showBaseFragment(
            CloudAuthFragment()
        )
    }

    private fun showBaseFragment(
        baseFragment: BaseFragment
    ) {
        supportFragmentManager.beginTransaction().let {
            it.add(
                R.id.container,
                baseFragment,
                baseFragment::class.java.name
            )
            it.addToBackStack(null)
            it.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}
