package com.smlnskgmail.jaman.randomnotes.components

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), FragmentResume {

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(getLayoutResId(), container, false)

    abstract fun getLayoutResId(): Int

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (showMenuInToolbar()) {
            inflater.inflate(getToolbarMenuResId(), menu)
        } else {
            menu.clear()
        }
        this.menu = menu
        onPostMenuInflated()
    }

    open fun onPostMenuInflated() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        handleMenuItemClick(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    fun getMenu() = menu

    abstract fun showMenuInToolbar(): Boolean

    open fun getToolbarMenuResId() = -1

    open fun handleMenuItemClick(menuItemId: Int) {

    }

}

interface FragmentResume {

    fun onFragmentResume()

}
