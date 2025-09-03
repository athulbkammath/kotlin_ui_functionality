package com.eto.binding_usage_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.databinding.FragmentAddNoteBinding
import com.eto.binding_usage_project.db.NoteDataBase
import com.eto.binding_usage_project.db.NoteEntity
import com.eto.binding_usage_project.util.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNote : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    private lateinit var noteEntity: NoteEntity

    private val noteDB: NoteDataBase by lazy {
        Room.databaseBuilder(
            requireContext(),
            NoteDataBase::class.java,
            Constants.NOTE_DATABASE
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration(false)
            .build()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val description = edtDesc.text.toString()

                if (description.isNotEmpty() && title.isNotEmpty()) {
                    noteEntity = NoteEntity(0, title, description)
                    noteDB.noteDao().insertNote(noteEntity)
                    findNavController().navigate(R.id.action_addNote_to_noteListFragment)
                } else {
                    Snackbar.make(root, "Please enter title and description", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
        return binding.root
    }
}