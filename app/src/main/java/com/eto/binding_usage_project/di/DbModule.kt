package com.eto.binding_usage_project.di

import android.content.Context
import androidx.room.Room
import com.eto.binding_usage_project.db.NoteDataBase
import com.eto.binding_usage_project.db.NoteEntity
import com.eto.binding_usage_project.util.Constants.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NoteDataBase::class.java, NOTE_DATABASE)
            .allowMainThreadQueries().fallbackToDestructiveMigration(false).build()

    @Provides
    @Singleton
    fun provideDao(db: NoteDataBase) = db.noteDao()


    @Provides
    @Singleton
    fun provideNoteEntity() = NoteEntity()
}