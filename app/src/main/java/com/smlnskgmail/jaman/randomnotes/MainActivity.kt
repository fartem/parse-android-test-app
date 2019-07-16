package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.entities.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddNoteListener {

    private lateinit var dataNotes: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeNotes()
        initializeFabMenu()
    }

    private fun initializeNotes() {
        dataNotes = Note.getAllNotes()
        notes.adapter = NotesAdapter(dataNotes)
    }

    private fun initializeFabMenu() {
        restore_notes.setOnClickListener {

        }
        sync_notes.setOnClickListener {

        }
        add_note.setOnClickListener {
            main_fab_menu.collapse()
            val addNoteBottomSheet = AddNoteBottomSheet()
            addNoteBottomSheet.addNoteCreationCallback(this)
            addNoteBottomSheet.show(supportFragmentManager,
                addNoteBottomSheet.javaClass.name)
        }
    }

    override fun onAddNote(note: Note) {
        dataNotes.add(note)
        (notes.adapter as NotesAdapter).validateLastNote()
    }

}
