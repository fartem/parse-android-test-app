package com.smlnskgmail.jaman.randomnotes.components

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private var menu: Menu? = null

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        setHasOptionsMenu(true)
    }

    fun resume() {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            showHomeAsUpEnabled()
        )
        activity!!.setTitle(
            getTitleResId()
        )
    }

    abstract fun showHomeAsUpEnabled(): Boolean

    abstract fun getTitleResId(): Int

    abstract fun showToolbarMenu(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        layoutResId(),
        container,
        false
    )

    abstract fun layoutResId(): Int

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        if (showMenuInToolbar()) {
            inflater.inflate(
                getToolbarMenuResId(),
                menu
            )
        } else {
            menu.clear()
        }
        this.menu = menu
        onMenuInflated()
    }

    open fun onMenuInflated() {

    }

    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {
        handleMenuItemClick(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    fun getMenu() = menu

    abstract fun showMenuInToolbar(): Boolean

    open fun getToolbarMenuResId() = -1

    open fun handleMenuItemClick(menuItemId: Int) {

    }

}
