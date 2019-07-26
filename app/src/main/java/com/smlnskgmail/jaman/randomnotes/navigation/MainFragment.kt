package com.smlnskgmail.jaman.randomnotes.navigation

import androidx.core.content.ContextCompat
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteBottomSheet
import com.smlnskgmail.jaman.randomnotes.components.bottomsheets.addnote.AddNoteListener
import com.smlnskgmail.jaman.randomnotes.components.dialogs.invite.InviteCallback
import com.smlnskgmail.jaman.randomnotes.components.dialogs.invite.InviteDialog
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import com.smlnskgmail.jaman.randomnotes.entities.Note
import com.smlnskgmail.jaman.randomnotes.parse.ParseUtils
import com.smlnskgmail.jaman.randomnotes.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), AddNoteListener, InviteCallback {

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
            actionWithNotes {
                share()
            }
        }
        restore_notes.setOnClickListener {
            actionWithNotes {
                restore()
            }
        }
        sync_notes.setOnClickListener {
            actionWithNotes {
                sync()
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

    private fun restore() {
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
                ParseObject.saveAllInBackground(parseToSave) {
                    if (it == null) {
                        refreshNotes()
                    }
                }
            }
        }
    }

    private fun sync() {
        val objectsToSave = mutableListOf<ParseObject>()
        for (note in dataNotes) {
            objectsToSave.add(note.getParseObject())
        }
        ParseObject.saveAllInBackground(objectsToSave) {
            if (it == null) {
                for (savedNote in objectsToSave) {
                    val note = dataNotes.firstOrNull { noteInList ->
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
                UIUtils.toast(context!!, getString(R.string.message_sign_in))
            }
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

    override fun onInviteAction(success: Boolean) {
        if (success) {
            UIUtils.toast(context!!, getString(R.string.message_invite_sent))
        } else {
            UIUtils.toast(context!!, getString(R.string.error_invite_sent))
        }
    }

    override fun handleMenuItemClick(menuItemId: Int) {
        when (menuItemId) {
            R.id.menu_login_action -> {
                if (ParseUtils.isAuthorized()) {
                    val parseUser = ParseUser.getCurrentUser()
                    if (ParseFacebookUtils.isLinked(parseUser)) {
                        AccessToken.setCurrentAccessToken(null)
                        if (LoginManager.getInstance() != null) {
                            LoginManager.getInstance().logOut()
                        }
                    }
                    ParseUser.logOutInBackground {
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
        val icon = if (ParseUtils.isAuthorized()) {
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