package com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote

import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.BaseBottomSheet
import com.smlnskgmail.jaman.randomnotes.entities.Note
import kotlinx.android.synthetic.main.bottom_sheet_add_note.*

class AddNoteBottomSheet : BaseBottomSheet() {

    private var addNoteTarget: AddNoteTarget? = null

    override fun initialize() {
        edit_title.requestFocus()
        save_note.setOnClickListener {
            val note = Note()
            note.title = edit_title.text.toString()
            note.subtitle = edit_subtitle.text.toString()
            note.save()
            addNoteTarget?.newNoteAdded(note)
            dismiss()
        }
    }

    fun addNoteCreationCallback(addNoteTarget: AddNoteTarget) {
        this.addNoteTarget = addNoteTarget
    }

    override fun getLayoutResId() = R.layout.bottom_sheet_add_note

}