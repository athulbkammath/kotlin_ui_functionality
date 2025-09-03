package com.eto.binding_usage_project.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
