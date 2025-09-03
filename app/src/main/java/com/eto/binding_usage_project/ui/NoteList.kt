package com.eto.binding_usage_project.ui

import NoteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eto.binding_usage_project.R
import com.eto.binding_usage_project.databinding.FragmentNoteListBinding
import com.eto.binding_usage_project.db.NoteDataBase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentNoteListBinding

    @Inject
    lateinit var noteDB: NoteDataBase

    private val noteAdapter by lazy {
        NoteAdapter { note ->
            val action = NoteListFragmentDirections
                .actionNoteListFragmentToUpdateNoteFragment(note.noteId)
            findNavController().navigate(action)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)

        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_addNote)
        }

        setupRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkItems()
    }

    private fun checkItems() {
        lifecycleScope.launch {
            val notes = noteDB.noteDao().getAllNotes()

            binding.apply {
                if (notes.isNotEmpty()) {
                    rvNoteList.visibility = View.VISIBLE
                    tvEmptyText.visibility = View.GONE
                    noteAdapter.differ.submitList(notes)
                } else {
                    rvNoteList.visibility = View.GONE
                    tvEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }
}


