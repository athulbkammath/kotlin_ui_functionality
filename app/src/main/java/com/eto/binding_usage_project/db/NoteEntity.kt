package com.eto.binding_usage_project.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eto.binding_usage_project.util.Constants.NOTE_TABLE


@Entity(tableName = NOTE_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    @ColumnInfo(name = "note_title")
    val noteTitle: String = "",
    @ColumnInfo(name = "note_description")
    val noteDescription: String = ""
)

