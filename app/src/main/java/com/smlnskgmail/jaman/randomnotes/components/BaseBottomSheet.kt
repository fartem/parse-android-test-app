package com.smlnskgmail.jaman.randomnotes.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smlnskgmail.jaman.randomnotes.R

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        layoutResId(),
        container
    )

    abstract fun layoutResId(): Int

    override fun getTheme(): Int {
        return R.style.AppBottomSheetStyle
    }

}
