package com.smlnskgmail.jaman.randomnotes.view.list

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseFragment
import com.smlnskgmail.jaman.randomnotes.components.LongSnackbar
import com.smlnskgmail.jaman.randomnotes.model.DataRepository
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.model.api.entities.Note
import com.smlnskgmail.jaman.randomnotes.presenter.list.NotesListPresenter
import com.smlnskgmail.jaman.randomnotes.presenter.list.NotesListPresenterImpl
import com.smlnskgmail.jaman.randomnotes.view.creation.NoteCreationBottomSheet
import com.smlnskgmail.jaman.randomnotes.view.creation.NoteCreationTarget
import com.smlnskgmail.jaman.randomnotes.view.invite.CloudInviteDialog
import com.smlnskgmail.jaman.randomnotes.view.invite.CloudInviteTarget
import com.smlnskgmail.jaman.randomnotes.view.list.recycler.NoteDeleteTarget
import com.smlnskgmail.jaman.randomnotes.view.list.recycler.NotesAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@Suppress("TooManyFunctions")
class NotesListFragment : BaseFragment(),
    NotesListView, NoteCreationTarget,
    NoteDeleteTarget,
    CloudInviteTarget {

    @Inject
    lateinit var dataRepository: DataRepository

    @Inject
    lateinit var cloudAuth: CloudAuth

    private lateinit var notesListPresenter: NotesListPresenter

    private val menuInitTasks = mutableListOf<MenuTask>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        (context!!.applicationContext as App).appComponent.inject(this)
        notesListPresenter = NotesListPresenterImpl()
        notesListPresenter.init(
            dataRepository,
            cloudAuth,
            this
        )

        share_access.setOnClickListener {
            actionWithNotes {
                notesListPresenter.shareNotes()
            }
        }
        restore_notes.setOnClickListener {
            actionWithNotes {
                notesListPresenter.restoreNotes()
            }
        }
        sync_notes.setOnClickListener {
            actionWithNotes {
                notesListPresenter.syncNotes()
            }
        }
        add_note.setOnClickListener {
            actionWithNotes {
                notesListPresenter.createNote()
            }
        }
    }

    override fun refreshNotesList(notes: MutableList<Note>) {
        notes_list.messageView = notes_list_empty_view
        notes_list.adapter = NotesAdapter(
            notes,
            this
        )
    }

    override fun addNote(note: Note) {
        notes_list.adapter?.notifyItemInserted(
            notes_list.adapter?.itemCount!!.minus(1)
        )
    }

    override fun deleteNote(note: Note) {
        notes_list.adapter?.notifyDataSetChanged()
    }

    override fun showShareSuccess() {
        LongSnackbar(
            notes_screen,
            getString(R.string.message_invite_sent)
        ).show()
    }

    override fun showAuthError() {
        LongSnackbar(
            notes_screen,
            getString(R.string.message_sign_in)
        ).show()
    }

    override fun showShareError() {
        LongSnackbar(
            notes_screen,
            getString(R.string.error_invite_sent)
        ).show()
    }

    override fun showRestoreError() {
        LongSnackbar(
            notes_screen,
            getString(R.string.error_cannot_restore_notes)
        ).show()
    }

    override fun showSyncError() {
        LongSnackbar(
            notes_screen,
            getString(R.string.error_cannot_sync_notes)
        ).show()
    }

    override fun openShareSender() {
        val inviteDialog = CloudInviteDialog(context!!)
        inviteDialog.setInviteCallback(this)
        inviteDialog.show()
    }

    override fun openAuthPage() {
        (activity as MainActivity).showAuthFragment()
    }

    override fun setAuthenticated() {
        if (getMenu() == null) {
            menuInitTasks.add(object : MenuTask {
                override fun execute() {
                    validateAuthMenuIcon(true)
                }
            })
        } else {
            validateAuthMenuIcon(true)
        }
    }

    override fun setUnauthenticated() {
        if (getMenu() == null) {
            menuInitTasks.add(object : MenuTask {
                override fun execute() {
                    validateAuthMenuIcon(false)
                }
            })
        } else {
            validateAuthMenuIcon(false)
        }
    }

    override fun openNoteCreator() {
        val addNoteBottomSheet = NoteCreationBottomSheet()
        addNoteBottomSheet.show(
            childFragmentManager,
            addNoteBottomSheet.javaClass.name
        )
    }

    private fun actionWithNotes(action: () -> Unit) {
        main_fab_menu.collapse()
        action()
    }

    override fun newNoteAdded(note: Note) {
        notesListPresenter.handleCreatedNote(note)
    }

    override fun onInviteAction(success: Boolean) {
        notesListPresenter.handleSharedMessage(
            success
        )
    }

    override fun onMenuInflated() {
        menuInitTasks.forEach {
            it.execute()
        }
        menuInitTasks.clear()
    }

    override fun handleMenuItemClick(menuItemId: Int) {
        when (menuItemId) {
            R.id.menu_auth_action -> {
                notesListPresenter.handleAuthRequest()
            }
        }
    }

    private fun validateAuthMenuIcon(
        isAuth: Boolean
    ) {
        val icon = if (isAuth) {
            R.drawable.ic_logout
        } else {
            R.drawable.ic_sign_in
        }
        getMenu()!!.findItem(
            R.id.menu_auth_action
        )!!.icon = ContextCompat.getDrawable(context!!, icon)
    }

    override fun onNoteDelete(note: Note) {
        notesListPresenter.handleNoteDeletion(
            note
        )
    }

    override fun getTitleResId() = R.string.app_name

    override fun showToolbarMenu() = true

    override fun layoutResId() = R.layout.fragment_main

    override fun showMenuInToolbar() = true

    override fun getToolbarMenuResId() = R.menu.menu_main

    interface MenuTask {

        fun execute()

    }

}
