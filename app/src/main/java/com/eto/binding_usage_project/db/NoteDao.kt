package com.eto.binding_usage_project.db
//package com.eto.roomdatabase.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.eto.binding_usage_project.util.Constants.NOTE_TABLE

//Required
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)

    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM $NOTE_TABLE ORDER BY noteId DESC")
    fun getAllNotes(): List<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE noteId = :id LIMIT 1")
    fun getNoteById(id: Int): NoteEntity


}