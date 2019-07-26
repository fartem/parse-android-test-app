package com.smlnskgmail.jaman.randomnotes.navigation

import androidx.core.content.ContextCompat
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.dialogs.invite.InviteCallback
import com.smlnskgmail.jaman.randomnotes.components.dialogs.invite.InviteDialog
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.components.ui.LongToast
import com.smlnskgmail.jaman.randomnotes.entities.Note
import com.smlnskgmail.jaman.randomnotes.parse.ParseApi
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), AddNoteListener, InviteCallback {

    private val notes: MutableList<Note> = mutableListOf()

    override fun onViewCreated() {
        super.onViewCreated()
        addNotesToList()
        setupFabMenu()
    }

    private fun addNotesToList() {
        notes.addAll(Note.getAllNotes())
        notes_list.setEmptyView(notes_list_empty_view)
        notes_list.adapter = NotesAdapter(notes)
    }

    private fun setupFabMenu() {
        share_access.setOnClickListener {
            actionWithNotes {
                share()
            }
        }
        restore_notes.setOnClickListener {
            actionWithNotes {
                ParseApi.restoreAllNotes(notes) {
                    if (it == null) {
                        refreshNotes()
                    } else {
                        LongToast.show(context!!, getString(R.string.error_cannot_restore_notes))
                    }
                }
            }
        }
        sync_notes.setOnClickListener {
            actionWithNotes {
                ParseApi.saveAllNotes(notes) {
                    LongToast.show(context!!, getString(R.string.error_cannot_sync_notes))
                }
            }
        }
        add_note.setOnClickListener {
            collapseMenuAndRun {
                addNote()
            }
        }
    }

    private fun share() {
        val inviteDialog = InviteDialog(context!!)
        inviteDialog.setInviteCallback(this)
        inviteDialog.show()
    }

    private fun addNote() {
        val addNoteBottomSheet = AddNoteBottomSheet()
        addNoteBottomSheet.addNoteCreationCallback(this)
        addNoteBottomSheet.show(activity!!.supportFragmentManager,
            addNoteBottomSheet.javaClass.name)
    }

    private fun collapseMenuAndRun(action: () -> Unit) {
        main_fab_menu.collapse()
        action()
    }

    private fun actionWithNotes(action: () -> Unit) {
        collapseMenuAndRun {
            if (ParseUser.getCurrentUser() != null) {
                action()
            } else {
                LongToast.show(context!!, getString(R.string.message_sign_in))
            }
        }
    }

    private fun refreshNotes() {
        notes.clear()
        notes.addAll(Note.getAllNotes())
        notes_list.adapter!!.notifyDataSetChanged()
    }

    override fun onAddNote(note: Note) {
        notes.add(note)
        (notes_list.adapter as NotesAdapter).validateLastNote()
    }

    override fun onInviteAction(success: Boolean) {
        if (success) {
            LongToast.show(context!!, getString(R.string.message_invite_sent))
        } else {
            LongToast.show(context!!, getString(R.string.error_invite_sent))
        }
    }

    override fun handleMenuItemClick(menuItemId: Int) {
        when (menuItemId) {
            R.id.menu_login_action -> {
                if (ParseApi.isAuthorized()) {
                    ParseApi.logout {
                        validateLoginMenuIcon()
                    }
                } else {
                    (activity as MainActivity).showLoginFragment()
                }
            }
        }
    }

    override fun onPostMenuInflated() {
        validateLoginMenuIcon()
    }

    private fun validateLoginMenuIcon() {
        val icon = if (ParseApi.isAuthorized()) {
            R.drawable.ic_logout
        } else {
            R.drawable.ic_login
        }
        getMenu().findItem(R.id.menu_login_action)!!
            .icon = ContextCompat.getDrawable(context!!, icon)
    }

    override fun getTitleResId() = R.string.title_main_fragment

    override fun showToolbarMenu() = true

    override fun getLayoutResId() = R.layout.fragment_main

    override fun showMenuInToolbar() = true

    override fun getToolbarMenuResId() = R.menu.menu_main

}