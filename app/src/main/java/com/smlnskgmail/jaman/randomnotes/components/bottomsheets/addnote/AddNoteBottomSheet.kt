package com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote

import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.BaseBottomSheet
import com.smlnskgmail.jaman.randomnotes.entities.Note
import kotlinx.android.synthetic.main.bottom_sheet_add_note.*

class AddNoteBottomSheet : BaseBottomSheet() {

    private var addNoteListener: AddNoteListener? = null

    override fun initialize() {
        add_note.setOnClickListener {
            val note = Note()
            note.title = edit_title.text.toString()
            note.subtitle = edit_subtitle.text.toString()
            note.save()
            addNoteListener?.onAddNote(note)
            dismiss()
        }
    }

    fun addNoteCreationCallback(addNoteListener: AddNoteListener) {
        this.addNoteListener = addNoteListener
    }

    override fun getLayoutResId() = R.layout.bottom_sheet_add_note

}