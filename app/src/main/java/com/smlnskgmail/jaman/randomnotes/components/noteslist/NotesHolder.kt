package com.smlnskgmail.jaman.randomnotes.components.noteslist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smlnskgmail.jaman.randomnotes.entities.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NotesHolder(itemView: View, private val noteDeleteListener: NoteDeleteListener)
    : RecyclerView.ViewHolder(itemView) {

    fun bind(note: Note) {
        itemView.note_title.text = note.title
        itemView.note_subtitle.text = note.subtitle
        itemView.delete_note.setOnClickListener {
            note.delete()
            noteDeleteListener.onNoteDelete(note.positionInList)
        }
    }

}