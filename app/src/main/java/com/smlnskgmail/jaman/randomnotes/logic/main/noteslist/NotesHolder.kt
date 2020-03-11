package com.smlnskgmail.jaman.randomnotes.logic.main.noteslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NotesHolder(
    itemView: View,
    private val noteDeleteTarget: NoteDeleteTarget
) : RecyclerView.ViewHolder(itemView) {

    fun bind(note: Note) {
        itemView.note_title.text = note.title
        itemView.note_subtitle.text = note.subtitle
        itemView.delete_note.setOnClickListener {
            noteDeleteTarget.onNoteDelete(
                note.positionInList
            )
        }
    }

}
