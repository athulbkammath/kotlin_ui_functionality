package com.eto.binding_usage_project.repository

import com.eto.binding_usage_project.db.NoteDao
import com.eto.binding_usage_project.db.NoteEntity
import javax.inject.Inject

class DbRepository
@Inject constructor(
    private val dao: NoteDao
) {

    fun insertNote(noteEntity: NoteEntity) = dao.insertNote(noteEntity)
    fun updateNote(noteEntity: NoteEntity) = dao.updateNote(noteEntity)
    fun deleteNote(noteEntity: NoteEntity) = dao.deleteNote(noteEntity)
    fun getNoteById(noteId: Int) = dao.getNoteById(noteId)
    fun getAllNotes() = dao.getAllNotes()
}

