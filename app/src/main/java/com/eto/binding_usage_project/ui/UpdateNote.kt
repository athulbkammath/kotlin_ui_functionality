package com.eto.binding_usage_project.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.eto.binding_usage_project.databinding.FragmentUpdateNoteBinding
import com.eto.binding_usage_project.db.NoteDataBase
import com.eto.binding_usage_project.db.NoteEntity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {

    private lateinit var binding: FragmentUpdateNoteBinding

    @Inject
    lateinit var noteDB: NoteDataBase

    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = args.noteId
        val note = noteDB.noteDao().getNoteById(noteId)

        binding.apply {
            edtTitle.setText(note.noteTitle)
            edtDesc.setText(note.noteDescription)

            btnDelete.setOnClickListener {
                note.let {
                    noteDB.noteDao().deleteNote(it)
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }

            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val description = edtDesc.text.toString()
                if (description.isNotEmpty() && title.isNotEmpty()) {
                    val updatedNote = NoteEntity(noteId, title, description)
                    noteDB.noteDao().updateNote(updatedNote)
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                } else {
                    Snackbar.make(root, "Please enter title and description", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}

