package com.smlnskgmail.jaman.randomnotes.navigation

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.entities.Note
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), AddNoteListener {

    private val dataNotes: MutableList<Note> = mutableListOf()

    override fun initialize() {
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
        val user = ParseUser.getCurrentUser()
        Note.deleteAllNotes()
        if (user != null) {
            val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(Note.TABLE_NOTE)
            val parseToSave = mutableListOf<ParseObject>()
            parseQuery.findInBackground { objects, e ->
                for (parseData in objects) {
                    val id = parseData.getLong(Note.COLUMN_ID)
                    var note = dataNotes.lastOrNull {
                        it.id == id
                    }
                    if (note == null) {
                        note = Note()
                    }
                    val parseNote = note.restoreFromParseObject(parseData)
                    note.save()
                    parseNote.put(Note.COLUMN_ID, note.id)
                    parseToSave.add(parseNote)
                }
                ParseObject.saveAllInBackground(parseToSave)
                refreshNotes()
            }
        }
    }

    private fun syncNotes() {
        val user = ParseUser.getCurrentUser()
        if (user != null) {
            val notes = Note.getAllNotes()
            val objectsToSave = mutableListOf<ParseObject>()
            for (note in notes) {
                if (note.parseObjectId == null) {
                    val parseNote = note.getParseObject(false, user)
                    objectsToSave.add(parseNote)
                }
            }
            ParseObject.saveAllInBackground(objectsToSave)
        }
    }

    private fun addNoteAction() {
        val addNoteBottomSheet = AddNoteBottomSheet()
        addNoteBottomSheet.addNoteCreationCallback(this)
        addNoteBottomSheet.show(activity!!.supportFragmentManager,
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

    override fun getTitleResId() = R.string.title_main_fragment

    override fun showToolbarMenu() = true

    override fun getLayoutResId() = R.layout.fragment_main

}