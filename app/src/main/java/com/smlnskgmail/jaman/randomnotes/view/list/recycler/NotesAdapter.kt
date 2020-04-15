package com.smlnskgmail.jaman.randomnotes.view.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val noteDeleteTarget: NoteDeleteTarget
) : RecyclerView.Adapter<NoteHolder>() {

    override fun onBindViewHolder(
        holder: NoteHolder,
        position: Int
    ) {
        holder.bind(
            notes[position]
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = NoteHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_note,
            parent,
            false
        ),
        noteDeleteTarget
    )

    override fun getItemCount() = notes.size

}
