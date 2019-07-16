package com.smlnskgmail.jaman.randomnotes.components.noteslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.entities.Note

class NotesAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NotesHolder>() {

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NotesHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_note, parent, false))

    override fun getItemCount() = notes.size

}