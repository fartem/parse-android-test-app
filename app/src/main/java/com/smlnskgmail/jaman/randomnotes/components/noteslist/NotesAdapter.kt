package com.smlnskgmail.jaman.randomnotes.components.noteslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.entities.note.Note

class NotesAdapter(private val notes: MutableList<Note>)
    : RecyclerView.Adapter<NotesHolder>(), NoteDeleteListener {

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val note = notes[position]
        note.positionInList = position
        holder.bind(note)
    }

    fun validateLastNote() {
        notifyItemInserted(itemCount - 1)
    }

    override fun onNoteDelete(position: Int) {
        notes.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NotesHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_note, parent, false), this)

    override fun getItemCount() = notes.size

}