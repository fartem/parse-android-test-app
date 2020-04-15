package com.smlnskgmail.jaman.randomnotes.view.creation

import android.os.Bundle
import android.view.View
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseBottomSheet
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.presenter.creation.NoteCreationPresenter
import com.smlnskgmail.jaman.randomnotes.presenter.creation.NoteCreationPresenterImpl
import kotlinx.android.synthetic.main.bottom_sheet_add_note.*

class NoteCreationBottomSheet : BaseBottomSheet(), NoteCreationView {

    private lateinit var noteCreationPresenter: NoteCreationPresenter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        noteCreationPresenter = NoteCreationPresenterImpl()
        noteCreationPresenter.init(this)
        save_note.setOnClickListener {
            noteCreationPresenter.createNote(
                title = edit_title.text.toString(),
                subtitle = edit_subtitle.text.toString()
            )
        }
    }

    override fun noteCreated(note: Note) {
        (parentFragment as NoteCreationTarget).newNoteAdded(
            note
        )
        dismiss()
    }

    override fun layoutResId() = R.layout.bottom_sheet_add_note

}
