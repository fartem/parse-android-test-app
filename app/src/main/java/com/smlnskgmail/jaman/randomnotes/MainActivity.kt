package com.smlnskgmail.jaman.randomnotes

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.smlnskgmail.jaman.randomnotes.components.noteslist.NotesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeNotes()
        initializeFabMenu()
    }

    private fun initializeNotes() {
//        notes.adapter = NotesAdapter()
    }

    private fun initializeFabMenu() {
        restore_notes.setOnClickListener {

        }
        sync_notes.setOnClickListener {

        }
        add_note.setOnClickListener {

        }
    }

}
