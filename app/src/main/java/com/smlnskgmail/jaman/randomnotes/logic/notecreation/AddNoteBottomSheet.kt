package com.smlnskgmail.jaman.randomnotes.logic.notecreation

import android.widget.EditText
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseBottomSheet
import com.smlnskgmail.jaman.randomnotes.repository.DataRepositoryAccessor
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note
import kotlinx.android.synthetic.main.bottom_sheet_add_note.*

class AddNoteBottomSheet : BaseBottomSheet() {

    private var addNoteTarget: AddNoteTarget? = null

    override fun initialize() {
        edit_title.requestFocus()
        save_note.setOnClickListener {
            val note = Note(
                title = edit_title.textInString(),
                subtitle = edit_subtitle.textInString()
            )
            DataRepositoryAccessor.get().saveNote(note)

            addNoteTarget?.newNoteAdded(note)
            dismiss()
        }
    }

    private fun EditText.textInString(): String {
        return text.toString()
    }

    fun addNoteCreationCallback(addNoteTarget: AddNoteTarget) {
        this.addNoteTarget = addNoteTarget
    }

    override fun getLayoutResId() = R.layout.bottom_sheet_add_note

    interface AddNoteTarget {

        fun newNoteAdded(note: Note)

    }

}
