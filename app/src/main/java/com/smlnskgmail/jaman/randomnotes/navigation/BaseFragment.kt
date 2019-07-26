package com.smlnskgmail.jaman.randomnotes.navigation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), FragmentResume {

    companion object {

        private const val TAG = "RN"

    }

    private lateinit var menu: Menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreated()
    }

    private fun setTitle() {
        activity!!.setTitle(getTitleResId())
    }

    open fun onViewCreated() {
        setHasOptionsMenu(true)
        setTitle()
    }

    override fun onFragmentResume() {
        setTitle()
    }

    abstract fun getTitleResId(): Int

    abstract fun showToolbarMenu(): Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater.inflate(getLayoutResId(), container, false)

    abstract fun getLayoutResId(): Int

    fun log(e: Exception) {
        Log.e(TAG, javaClass.name, e)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (showMenuInToolbar()) {
            inflater!!.inflate(getToolbarMenuResId(), menu)
        } else {
            menu?.clear()
        }
        this.menu = menu!!
        onPostMenuInflated()
    }

    open fun onPostMenuInflated() {}

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        handleMenuItemClick(item!!.itemId)
        return super.onOptionsItemSelected(item)
    }

    fun getMenu() = menu

    abstract fun showMenuInToolbar(): Boolean

    open fun getToolbarMenuResId() = -1

    open fun handleMenuItemClick(menuItemId: Int) {}

}