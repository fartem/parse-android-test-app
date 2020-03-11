package com.smlnskgmail.jaman.randomnotes.logic.notecreation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseBottomSheet
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import kotlinx.android.synthetic.main.bottom_sheet_add_note.*

class AddNoteBottomSheet : BaseBottomSheet() {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        edit_title.requestFocus()
        save_note.setOnClickListener {
            val note = Note(
                title = edit_title.textInString(),
                subtitle = edit_subtitle.textInString()
            )

            (parentFragment as AddNoteTarget).newNoteAdded(note)
            dismiss()
        }
    }

    private fun EditText.textInString(): String {
        return text.toString()
    }

    override fun layoutResId() = R.layout.bottom_sheet_add_note

}
