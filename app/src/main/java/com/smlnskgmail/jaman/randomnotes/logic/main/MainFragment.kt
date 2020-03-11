package com.smlnskgmail.jaman.randomnotes.logic.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.Application
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.fragments.BaseFragment
import com.smlnskgmail.jaman.randomnotes.components.views.LongToast
import com.smlnskgmail.jaman.randomnotes.logic.invite.InviteDialog
import com.smlnskgmail.jaman.randomnotes.logic.invite.InviteUserTarget
import com.smlnskgmail.jaman.randomnotes.logic.main.noteslist.NoteDeleteTarget
import com.smlnskgmail.jaman.randomnotes.logic.main.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.logic.notecreation.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.logic.notecreation.AddNoteTarget
import com.smlnskgmail.jaman.randomnotes.logic.repository.DataRepository
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.entities.Note
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment(), AddNoteTarget, InviteUserTarget, NoteDeleteTarget {

    private val notes: MutableList<Note> = mutableListOf()

    @Inject
    lateinit var dataRepository: DataRepository

    @Inject
    lateinit var cloudAuth: CloudAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Application.applicationComponent.inject(this)
        addNotesToList()
        setupFabMenu()
    }

    private fun addNotesToList() {
        notes.addAll(dataRepository.allNotes())
        notes_list.messageView = notes_list_empty_view
        notes_list.adapter = NotesAdapter(notes, this)
    }

    private fun setupFabMenu() {
        share_access.setOnClickListener {
            actionWithNotes {
                share()
            }
        }
        restore_notes.setOnClickListener {
            actionWithNotes {
                dataRepository.restoreAllNotes(notes) { newNotes, e ->
                    if (e == null) {
                        dataRepository.saveNotes(newNotes)
                        refreshNotes()
                    } else {
                        LongToast(
                            context!!,
                            getString(R.string.error_cannot_restore_notes)
                        ).show()
                    }
                }
            }
        }
        sync_notes.setOnClickListener {
            actionWithNotes {
                dataRepository.syncNotes(notes) {
                    LongToast(
                        context!!,
                        getString(R.string.error_cannot_sync_notes)
                    ).show()
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
        addNoteBottomSheet.show(
            activity!!.supportFragmentManager,
            addNoteBottomSheet.javaClass.name
        )
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
                LongToast(
                    context!!,
                    getString(R.string.message_sign_in)
                ).show()
            }
        }
    }

    private fun refreshNotes() {
        notes.clear()
        notes.addAll(dataRepository.allNotes())
        notes_list.adapter!!.notifyDataSetChanged()
    }

    override fun newNoteAdded(note: Note) {
        dataRepository.saveNote(note)
        notes.add(note)
        (notes_list.adapter as NotesAdapter).validateLastNote()
    }

    override fun onInviteAction(success: Boolean) {
        if (success) {
            LongToast(
                context!!,
                getString(R.string.message_invite_sent)
            ).show()
        } else {
            LongToast(
                context!!,
                getString(R.string.error_invite_sent)
            ).show()
        }
    }

    override fun handleMenuItemClick(menuItemId: Int) {
        when (menuItemId) {
            R.id.menu_login_action -> {
                if (cloudAuth.isAuthorized()) {
                    cloudAuth.logOut {
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
        val icon = if (cloudAuth.isAuthorized()) {
            R.drawable.ic_logout
        } else {
            R.drawable.ic_login
        }
        getMenu().findItem(
            R.id.menu_login_action
        )!!.icon = ContextCompat.getDrawable(context!!, icon)
    }

    override fun onNoteDelete(position: Int) {
        dataRepository.delete(
            notes.removeAt(position)
        )
        notes_list.adapter!!.notifyItemRemoved(position)
    }

    override fun getTitleResId() = R.string.title_main_fragment

    override fun showToolbarMenu() = true

    override fun layoutResId() = R.layout.fragment_main

    override fun showMenuInToolbar() = true

    override fun getToolbarMenuResId() = R.menu.menu_main

}
