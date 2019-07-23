package com.smlnskgmail.jaman.randomnotes.navigation

import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.dialogs.ShareDialog
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.entities.Note
import com.smlnskgmail.jaman.randomnotes.utils.UIUtils
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
        share_access.setOnClickListener {
            collapseMenuAndRun {
                share()
            }
        }
        restore_notes.setOnClickListener {
            collapseMenuAndRun {
                restoreNotes()
            }
        }
        sync_notes.setOnClickListener {
            collapseMenuAndRun {
                syncNotes()
            }
        }
        add_note.setOnClickListener {
            collapseMenuAndRun {
                addNoteAction()
            }
        }
    }

    private fun collapseMenuAndRun(action: () -> Unit) {
        main_fab_menu.collapse()
        action()
    }

    private fun share() {
        actionWithNotes {
            val shareDialog = ShareDialog(context!!)
            shareDialog.show()
        }
    }

    private fun restoreNotes() {
        actionWithNotes {
            val parseQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(Note.TABLE_NOTE)
            val parseToSave = mutableListOf<ParseObject>()
            parseQuery.findInBackground { objects, e ->
                if (objects.isNotEmpty()) {
                    Note.deleteAllNotes()
                    for (parseData in objects) {
                        val id = parseData.getLong(Note.COLUMN_ID)
                        var note = dataNotes.lastOrNull {
                            it.id == id
                        }
                        if (note == null) {
                            note = Note()
                        }
                        if (note.parseObjectId == null) {
                            note.parseObjectId = parseData.objectId
                        }
                        val parseNote = note.restoreFromParseObject(parseData)
                        note.save()
                        parseNote.put(Note.COLUMN_ID, note.id)
                        parseToSave.add(parseNote)
                    }
                    ParseObject.saveAllInBackground(parseToSave)
                    refreshNotes()
                } else if (e != null) {
                    UIUtils.toast(context!!, "")
                }
            }
        }
    }

    private fun syncNotes() {
        actionWithNotes {
            val notes = Note.getAllNotes()
            val objectsToSave = mutableListOf<ParseObject>()
            for (note in notes) {
                objectsToSave.add(note.getParseObject())
            }
            ParseObject.saveAllInBackground(objectsToSave) {
                if (it == null) {
                    for (savedNote in objectsToSave) {
                        val note = notes.firstOrNull { noteInList ->
                            noteInList.id == savedNote.get(Note.COLUMN_ID)
                        }
                        if (note != null) {
                            note.parseObjectId = savedNote.objectId
                            note.save()
                        }
                    }
                }
            }
        }
    }

    private fun actionWithNotes(action: () -> Unit) {
        if (ParseUser.getCurrentUser() != null) {
            action()
        } else {
            UIUtils.toast(context!!, getString(R.string.message_sign_in))
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