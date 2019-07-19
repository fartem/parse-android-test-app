package com.smlnskgmail.jaman.randomnotes.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
    }

    abstract fun initialize()

    abstract fun getTitleResId(): Int

    abstract fun showToolbarMenu(): Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater.inflate(getLayoutResId(), container, false)

    abstract fun getLayoutResId(): Int

}