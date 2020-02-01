package com.smlnskgmail.jaman.randomnotes.logic.notecreation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.BaseBottomSheet
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import kotlinx.android.synthetic.main.bottom_sheet_add_note.*

class AddNoteBottomSheet : BaseBottomSheet() {

    private var addNoteTarget: AddNoteTarget? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_title.requestFocus()
        save_note.setOnClickListener {
            val note = Note(
                title = edit_title.textInString(),
                subtitle = edit_subtitle.textInString()
            )

            addNoteTarget?.newNoteAdded(note)
            dismiss()
        }
    }

    private fun EditText.textInString(): String {
        return text.toString()
    }

    fun addNoteCreationTarget(addNoteTarget: AddNoteTarget) {
        this.addNoteTarget = addNoteTarget
    }

    override fun getLayoutResId() = R.layout.bottom_sheet_add_note

}
