package com.smlnskgmail.jaman.randomnotes.navigation

import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.dialogs.ShareDialog
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.entities.Note
import com.smlnskgmail.jaman.randomnotes.parse.ParseApi
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
        notes_list.setEmptyView(notes_list_empty_view)
        notes_list.adapter = NotesAdapter(dataNotes)
    }

    private fun initializeFabMenu() {
        share_access.setOnClickListener {
            collapseMenuAndRun {
                actionWithNotes {
                    val shareDialog = ShareDialog(context!!)
                    shareDialog.show()
                }
            }
        }
        restore_notes.setOnClickListener {
            collapseMenuAndRun {
                actionWithNotes {
                    ParseApi.restoreNotesFromServer(dataNotes) { success ->
                        if (success) {
                            refreshNotes()
                        } else {

                        }
                    }
                }
            }
        }
        sync_notes.setOnClickListener {
            collapseMenuAndRun {
                actionWithNotes {
                    ParseApi.syncNotesWithServer(dataNotes)
                }
            }
        }
        add_note.setOnClickListener {
            collapseMenuAndRun {
                val addNoteBottomSheet = AddNoteBottomSheet()
                addNoteBottomSheet.addNoteCreationCallback(this)
                addNoteBottomSheet.show(activity!!.supportFragmentManager,
                    addNoteBottomSheet.javaClass.name)
            }
        }
    }

    private fun collapseMenuAndRun(action: () -> Unit) {
        main_fab_menu.collapse()
        action()
    }

    private fun actionWithNotes(action: () -> Unit) {
        if (ParseUser.getCurrentUser() != null) {
            action()
        } else {
            UIUtils.toast(context!!, getString(R.string.message_sign_in))
        }
    }

    private fun refreshNotes() {
        dataNotes.clear()
        dataNotes.addAll(Note.getAllNotes())
        notes_list.adapter!!.notifyDataSetChanged()
    }

    override fun onAddNote(note: Note) {
        dataNotes.add(note)
        (notes_list.adapter as NotesAdapter).validateLastNote()
    }

    override fun getTitleResId() = R.string.title_main_fragment

    override fun showToolbarMenu() = true

    override fun getLayoutResId() = R.layout.fragment_main

}