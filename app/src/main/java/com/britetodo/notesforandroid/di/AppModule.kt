package com.britetodo.notesforandroid.di

import android.content.Context
import androidx.room.Room
import com.britetodo.notesforandroid.data.db.AppDatabase
import com.britetodo.notesforandroid.data.db.NotebookDao
import com.britetodo.notesforandroid.data.db.PageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideNotebookDao(db: AppDatabase): NotebookDao = db.notebookDao()

    @Provides
    fun providePageDao(db: AppDatabase): PageDao = db.pageDao()
}
