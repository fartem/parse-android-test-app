package com.smlnskgmail.jaman.randomnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseObject
import com.parse.ParseQuery
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.entities.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddNoteListener {

    private val dataNotes: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeNotes()
        initializeFabMenu()
    }

    private fun initializeNotes() {
        dataNotes.addAll(Note.getAllNotes())
        notes.adapter = NotesAdapter(dataNotes)
    }

    private fun initializeFabMenu() {
        restore_notes.setOnClickListener {
            main_fab_menu.collapse()
            restoreNotes()
        }
        sync_notes.setOnClickListener {
            main_fab_menu.collapse()
            syncNotes()
        }
        add_note.setOnClickListener {
            main_fab_menu.collapse()
            addNoteAction()
        }
    }

    private fun restoreNotes() {
        val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery("note")
        val parseToSave = mutableListOf<ParseObject>()
        parseQuery.findInBackground { objects, e ->
            for (parseData in objects) {
                val id = parseData.getLong("id")
                var note = dataNotes.lastOrNull {
                    it.id == id
                }
                if (note == null) {
                    note = Note()
                }
                parseToSave.add(note.restoreFromParseObject(parseData))
            }
            ParseObject.saveAllInBackground(parseToSave)
            refreshNotes()
        }
    }

    private fun syncNotes() {
        val notes = Note.getAllNotes()
        val objectsToSave = mutableListOf<ParseObject>()
        for (note in notes) {
            objectsToSave.add(note.getParseObject())
        }
        ParseObject.saveAllInBackground(objectsToSave)
    }

    private fun addNoteAction() {
        val addNoteBottomSheet = AddNoteBottomSheet()
        addNoteBottomSheet.addNoteCreationCallback(this)
        addNoteBottomSheet.show(supportFragmentManager,
            addNoteBottomSheet.javaClass.name)
    }

    private fun refreshNotes() {
        dataNotes.clear()
        dataNotes.addAll(Note.getAllNotes())
        notes.adapter!!.notifyDataSetChanged()
    }

    override fun onAddNote(note: Note) {
        dataNotes.add(note)
        (notes.adapter as NotesAdapter).validateLastNote()
    }

}
