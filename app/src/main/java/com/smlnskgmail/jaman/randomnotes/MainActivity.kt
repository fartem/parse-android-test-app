package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.navigation.BaseFragment
import com.smlnskgmail.jaman.randomnotes.navigation.LoginFragment
import com.smlnskgmail.jaman.randomnotes.navigation.MainFragment
import com.smlnskgmail.jaman.randomnotes.parse.ParseUtils

class MainActivity : AppCompatActivity() {

    private var loginMenu: MenuItem? = null

    private val fragmentBackStackListener = FragmentManager.OnBackStackChangedListener {
        val currentFragment = getCurrentFragment()
        if (currentFragment is BaseFragment) {
            setTitleToToolbar(currentFragment.getTitleResId())
            validateMenu(currentFragment.showToolbarMenu())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login_action -> {
                if (ParseUtils.isAuthorized()) {
                    val parseUser = ParseUser.getCurrentUser()
                    if (ParseFacebookUtils.isLinked(parseUser)) {
                        AccessToken.setCurrentAccessToken(null)
                        if (LoginManager.getInstance() != null) {
                            LoginManager.getInstance().logOut()
                        }
                    }
                    ParseUser.logOutInBackground {
                        if (it == null) {
                            validateLoginIcon()
                        }
                    }
                } else {
                    showBaseFragment(LoginFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_auth, menu)
        loginMenu = menu!!.findItem(R.id.menu_login_action)
        validateLoginIcon()
        return super.onCreateOptionsMenu(menu)
    }

    private fun validateLoginIcon() {
        val icon = if (ParseUtils.isAuthorized()) {
            R.drawable.ic_logout
        } else {
            R.drawable.ic_login
        }
        loginMenu!!.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun showBaseFragment(baseFragment: BaseFragment, inBackStack: Boolean= true) {
        validateMenu(baseFragment.showToolbarMenu())
        setTitleToToolbar(baseFragment.getTitleResId())
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container, baseFragment)
        if (inBackStack) {
            fragmentTransaction.addToBackStack(baseFragment::class.java.name)
        }
        fragmentTransaction.commit()
    }

    private fun setTitleToToolbar(resId: Int) {
        setTitle(resId)
    }

    private fun validateMenu(show: Boolean) {
        if (loginMenu != null) {
            loginMenu!!.isVisible = show
        }
    }

    fun loginComplete() {
        showMainFragment()
        validateLoginIcon()
    }

    fun loginError() {
        showMainFragment()
    }

    private fun showMainFragment() {
        val mainFragment: MainFragment = (supportFragmentManager
            .findFragmentByTag(MainFragment::class.java.name) ?: MainFragment()) as MainFragment
        showBaseFragment(mainFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(fragmentBackStackListener)
    }

}
