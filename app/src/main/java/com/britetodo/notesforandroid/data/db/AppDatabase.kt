package com.britetodo.notesforandroid.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NotebookEntity::class, PageEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notebookDao(): NotebookDao
    abstract fun pageDao(): PageDao

    companion object {
        const val DATABASE_NAME = "notes_for_android.db"
    }
}
