package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.smlnskgmail.jaman.randomnotes.navigation.BaseFragment
import com.smlnskgmail.jaman.randomnotes.navigation.LoginFragment
import com.smlnskgmail.jaman.randomnotes.navigation.MainFragment

class MainActivity : AppCompatActivity() {

    private var loginMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(MainFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login_action -> {
                showFragment(LoginFragment(), true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auth, menu)
        loginMenu = menu!!.findItem(R.id.menu_login_action)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showFragment(baseFragment: BaseFragment, hideMenu: Boolean = false) {
        validateMenu(hideMenu)
        setTitleToToolbar(baseFragment.getTitleResId())
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, baseFragment)
            .addToBackStack(baseFragment::class.java.name)
            .commit()
    }

    private fun setTitleToToolbar(resId: Int) {
        setTitle(resId)
    }

    private fun validateMenu(hide: Boolean) {
        if (loginMenu != null) {
            loginMenu!!.isVisible = !hide
        }
    }

}
