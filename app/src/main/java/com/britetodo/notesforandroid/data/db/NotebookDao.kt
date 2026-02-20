package com.britetodo.notesforandroid.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotebookDao {
    @Query("SELECT * FROM notebooks WHERE isArchived = 0 ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<NotebookEntity>>

    @Query("SELECT * FROM notebooks WHERE isFavorite = 1 AND isArchived = 0 ORDER BY updatedAt DESC")
    fun observeFavorites(): Flow<List<NotebookEntity>>

    @Query("SELECT * FROM notebooks WHERE isArchived = 1 ORDER BY updatedAt DESC")
    fun observeArchived(): Flow<List<NotebookEntity>>

    @Query("SELECT * FROM notebooks WHERE id = :id")
    suspend fun getById(id: String): NotebookEntity?

    @Query("SELECT * FROM notebooks WHERE id = :id")
    fun observeById(id: String): Flow<NotebookEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notebook: NotebookEntity)

    @Update
    suspend fun update(notebook: NotebookEntity)

    @Delete
    suspend fun delete(notebook: NotebookEntity)

    @Query("DELETE FROM notebooks WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE notebooks SET isFavorite = :isFavorite, updatedAt = :updatedAt WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE notebooks SET isArchived = :isArchived, updatedAt = :updatedAt WHERE id = :id")
    suspend fun setArchived(id: String, isArchived: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE notebooks SET pageCount = :count, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePageCount(id: String, count: Int, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE notebooks SET lastPageId = :pageId, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateLastPage(id: String, pageId: String, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM notebooks WHERE isArchived = 0")
    suspend fun countActive(): Int
}
