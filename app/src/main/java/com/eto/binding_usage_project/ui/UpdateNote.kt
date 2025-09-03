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

        // ✅ Fetch note directly from DB instead of passing title/desc in args
        val note = noteDB.noteDao().getNoteById(noteId)

        binding.apply {
            edtTitle.setText(note?.noteTitle ?: "")
            edtDesc.setText(note?.noteDescription ?: "")

            btnDelete.setOnClickListener {
                note?.let {
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


//class UpdateNoteFragment : Fragment() {
//
//    private lateinit var binding: FragmentUpdateNoteBinding
//    private val noteDB: NoteDataBase by lazy {
//        Room.databaseBuilder(requireContext(), NoteDataBase::class.java, Constants.NOTE_DATABASE)
//            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration(false)
//            .build()
//    }
//
//    private val args: UpdateNoteFragmentArgs by navArgs()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val noteId = args.noteId
//        val defaultTitle = args.noteTitle
//        val defaultDescription = args.noteDescription
//
//        binding.apply {
//            edtTitle.setText(defaultTitle)
//            edtDesc.setText(defaultDescription)
//
//            btnDelete.setOnClickListener {
//                val noteEntity =
//                    NoteEntity(noteId, edtTitle.text.toString(), edtDesc.text.toString())
//                noteDB.noteDao().deleteNote(noteEntity)
//                requireActivity().onBackPressedDispatcher.onBackPressed()
//            }
//
//            btnSave.setOnClickListener {
//                val title = edtTitle.text.toString()
//                val description = edtDesc.text.toString()
//                if (description.isNotEmpty() && title.isNotEmpty()) {
//                    val noteEntity = NoteEntity(noteId, title, description)
//                    noteDB.noteDao().updateNote(noteEntity)
//                    requireActivity().onBackPressedDispatcher.onBackPressed()
//                } else {
//                    Snackbar.make(root, "Please enter title and description", Snackbar.LENGTH_LONG)
//                        .show()
//                }
//            }
//        }
//    }
//}

//class UpdateNoteFragment : Fragment() {
//
//    private lateinit var binding: FragmentUpdateNoteBinding
//    private val noteDB: NoteDataBase by lazy {
//        Room.databaseBuilder(requireContext(), NoteDataBase::class.java, Constants.NOTE_DATABASE)
//            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration(false)
//            .build()
//    }
//
//    private var noteId = 0
//    private var defaultTitle = ""
//    private var defaultDescription = ""
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        arguments?.let {
//            noteId = it.getInt(Constants.BUNDLE_NOTE_ID)
//        }
//
//        binding.apply {
//            defaultTitle = noteDB.dao().getNoteById(noteId).noteTitle
//            defaultDescription = noteDB.dao().getNoteById(noteId).noteDescription
//
//            edtTitle.setText(defaultTitle)
//            edtDesc.setText(defaultDescription)
//
//            btnDelete.setOnClickListener {
//                val noteEntity =
//                    NoteEntity(noteId, edtTitle.text.toString(), edtDesc.text.toString())
//                noteDB.dao().deleteNote(noteEntity)
//                requireActivity().onBackPressedDispatcher.onBackPressed()
//            }
//
//            btnSave.setOnClickListener {
//                val title = edtTitle.text.toString()
//                val description = edtDesc.text.toString()
//                if (description.isNotEmpty() || title.isNotEmpty()) {
//                    val noteEntity =
//                        NoteEntity(noteId, title, description)
//                    noteDB.dao().updateNote(noteEntity)
//                    requireActivity().onBackPressedDispatcher.onBackPressed()
//                } else {
//                    Snackbar.make(root, "Please enter title and description", Snackbar.LENGTH_LONG)
//                        .show()
//                }
//            }
//        }
//    }
//}
