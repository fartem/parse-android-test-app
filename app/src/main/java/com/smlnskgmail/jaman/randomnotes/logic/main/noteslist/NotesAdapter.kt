package com.smlnskgmail.jaman.randomnotes.logic.main.noteslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val noteDeleteTarget: NoteDeleteTarget
) : RecyclerView.Adapter<NotesHolder>() {

    override fun onBindViewHolder(
        holder: NotesHolder,
        position: Int
    ) {
        val note = notes[position]
        note.positionInList = position
        holder.bind(note)
    }

    fun validateLastNote() {
        if (notes.size == 1) {
            notifyDataSetChanged()
        } else {
            notifyItemInserted(itemCount - 1)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NotesHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_note,
            parent,
            false
        ),
        noteDeleteTarget
    )

    override fun getItemCount() = notes.size

}
