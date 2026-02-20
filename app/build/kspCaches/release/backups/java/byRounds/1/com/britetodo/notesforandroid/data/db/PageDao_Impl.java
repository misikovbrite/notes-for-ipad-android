package com.britetodo.notesforandroid.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PageDao_Impl implements PageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PageEntity> __insertionAdapterOfPageEntity;

  private final EntityDeletionOrUpdateAdapter<PageEntity> __deletionAdapterOfPageEntity;

  private final EntityDeletionOrUpdateAdapter<PageEntity> __updateAdapterOfPageEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByNotebook;

  private final SharedSQLiteStatement __preparedStmtOfUpdateThumbnail;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDrawingPath;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTextNotes;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStickers;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOrder;

  public PageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPageEntity = new EntityInsertionAdapter<PageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pages` (`id`,`notebookId`,`templateId`,`order`,`title`,`date`,`drawingFilePath`,`textNotesJson`,`stickersJson`,`thumbnailPath`,`hasContent`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PageEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNotebookId());
        statement.bindString(3, entity.getTemplateId());
        statement.bindLong(4, entity.getOrder());
        if (entity.getTitle() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTitle());
        }
        if (entity.getDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDate());
        }
        if (entity.getDrawingFilePath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDrawingFilePath());
        }
        if (entity.getTextNotesJson() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTextNotesJson());
        }
        if (entity.getStickersJson() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getStickersJson());
        }
        if (entity.getThumbnailPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getThumbnailPath());
        }
        final int _tmp = entity.getHasContent() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfPageEntity = new EntityDeletionOrUpdateAdapter<PageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pages` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PageEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfPageEntity = new EntityDeletionOrUpdateAdapter<PageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pages` SET `id` = ?,`notebookId` = ?,`templateId` = ?,`order` = ?,`title` = ?,`date` = ?,`drawingFilePath` = ?,`textNotesJson` = ?,`stickersJson` = ?,`thumbnailPath` = ?,`hasContent` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PageEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNotebookId());
        statement.bindString(3, entity.getTemplateId());
        statement.bindLong(4, entity.getOrder());
        if (entity.getTitle() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTitle());
        }
        if (entity.getDate() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDate());
        }
        if (entity.getDrawingFilePath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDrawingFilePath());
        }
        if (entity.getTextNotesJson() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTextNotesJson());
        }
        if (entity.getStickersJson() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getStickersJson());
        }
        if (entity.getThumbnailPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getThumbnailPath());
        }
        final int _tmp = entity.getHasContent() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
        statement.bindString(14, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pages WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByNotebook = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM pages WHERE notebookId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateThumbnail = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pages SET thumbnailPath = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDrawingPath = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pages SET drawingFilePath = ?, hasContent = 1, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTextNotes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pages SET textNotesJson = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStickers = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pages SET stickersJson = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateOrder = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE pages SET `order` = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PageEntity page, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPageEntity.insert(page);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PageEntity page, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPageEntity.handle(page);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PageEntity page, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPageEntity.handle(page);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByNotebook(final String notebookId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByNotebook.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, notebookId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByNotebook.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateThumbnail(final String id, final String path, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateThumbnail.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, path);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateThumbnail.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDrawingPath(final String id, final String path, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDrawingPath.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, path);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateDrawingPath.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTextNotes(final String id, final String json, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTextNotes.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, json);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateTextNotes.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStickers(final String id, final String json, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStickers.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, json);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStickers.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrder(final String id, final int order,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOrder.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, order);
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateOrder.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PageEntity>> observeByNotebook(final String notebookId) {
    final String _sql = "SELECT * FROM pages WHERE notebookId = ? ORDER BY `order` ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, notebookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pages"}, new Callable<List<PageEntity>>() {
      @Override
      @NonNull
      public List<PageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNotebookId = CursorUtil.getColumnIndexOrThrow(_cursor, "notebookId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDrawingFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingFilePath");
          final int _cursorIndexOfTextNotesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "textNotesJson");
          final int _cursorIndexOfStickersJson = CursorUtil.getColumnIndexOrThrow(_cursor, "stickersJson");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfHasContent = CursorUtil.getColumnIndexOrThrow(_cursor, "hasContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PageEntity> _result = new ArrayList<PageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PageEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNotebookId;
            _tmpNotebookId = _cursor.getString(_cursorIndexOfNotebookId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final Long _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            }
            final String _tmpDrawingFilePath;
            if (_cursor.isNull(_cursorIndexOfDrawingFilePath)) {
              _tmpDrawingFilePath = null;
            } else {
              _tmpDrawingFilePath = _cursor.getString(_cursorIndexOfDrawingFilePath);
            }
            final String _tmpTextNotesJson;
            if (_cursor.isNull(_cursorIndexOfTextNotesJson)) {
              _tmpTextNotesJson = null;
            } else {
              _tmpTextNotesJson = _cursor.getString(_cursorIndexOfTextNotesJson);
            }
            final String _tmpStickersJson;
            if (_cursor.isNull(_cursorIndexOfStickersJson)) {
              _tmpStickersJson = null;
            } else {
              _tmpStickersJson = _cursor.getString(_cursorIndexOfStickersJson);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final boolean _tmpHasContent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasContent);
            _tmpHasContent = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PageEntity(_tmpId,_tmpNotebookId,_tmpTemplateId,_tmpOrder,_tmpTitle,_tmpDate,_tmpDrawingFilePath,_tmpTextNotesJson,_tmpStickersJson,_tmpThumbnailPath,_tmpHasContent,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getByNotebook(final String notebookId,
      final Continuation<? super List<PageEntity>> $completion) {
    final String _sql = "SELECT * FROM pages WHERE notebookId = ? ORDER BY `order` ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, notebookId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PageEntity>>() {
      @Override
      @NonNull
      public List<PageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNotebookId = CursorUtil.getColumnIndexOrThrow(_cursor, "notebookId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDrawingFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingFilePath");
          final int _cursorIndexOfTextNotesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "textNotesJson");
          final int _cursorIndexOfStickersJson = CursorUtil.getColumnIndexOrThrow(_cursor, "stickersJson");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfHasContent = CursorUtil.getColumnIndexOrThrow(_cursor, "hasContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PageEntity> _result = new ArrayList<PageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PageEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNotebookId;
            _tmpNotebookId = _cursor.getString(_cursorIndexOfNotebookId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final Long _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            }
            final String _tmpDrawingFilePath;
            if (_cursor.isNull(_cursorIndexOfDrawingFilePath)) {
              _tmpDrawingFilePath = null;
            } else {
              _tmpDrawingFilePath = _cursor.getString(_cursorIndexOfDrawingFilePath);
            }
            final String _tmpTextNotesJson;
            if (_cursor.isNull(_cursorIndexOfTextNotesJson)) {
              _tmpTextNotesJson = null;
            } else {
              _tmpTextNotesJson = _cursor.getString(_cursorIndexOfTextNotesJson);
            }
            final String _tmpStickersJson;
            if (_cursor.isNull(_cursorIndexOfStickersJson)) {
              _tmpStickersJson = null;
            } else {
              _tmpStickersJson = _cursor.getString(_cursorIndexOfStickersJson);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final boolean _tmpHasContent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasContent);
            _tmpHasContent = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PageEntity(_tmpId,_tmpNotebookId,_tmpTemplateId,_tmpOrder,_tmpTitle,_tmpDate,_tmpDrawingFilePath,_tmpTextNotesJson,_tmpStickersJson,_tmpThumbnailPath,_tmpHasContent,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final String id, final Continuation<? super PageEntity> $completion) {
    final String _sql = "SELECT * FROM pages WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PageEntity>() {
      @Override
      @Nullable
      public PageEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNotebookId = CursorUtil.getColumnIndexOrThrow(_cursor, "notebookId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDrawingFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingFilePath");
          final int _cursorIndexOfTextNotesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "textNotesJson");
          final int _cursorIndexOfStickersJson = CursorUtil.getColumnIndexOrThrow(_cursor, "stickersJson");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfHasContent = CursorUtil.getColumnIndexOrThrow(_cursor, "hasContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final PageEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNotebookId;
            _tmpNotebookId = _cursor.getString(_cursorIndexOfNotebookId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final Long _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            }
            final String _tmpDrawingFilePath;
            if (_cursor.isNull(_cursorIndexOfDrawingFilePath)) {
              _tmpDrawingFilePath = null;
            } else {
              _tmpDrawingFilePath = _cursor.getString(_cursorIndexOfDrawingFilePath);
            }
            final String _tmpTextNotesJson;
            if (_cursor.isNull(_cursorIndexOfTextNotesJson)) {
              _tmpTextNotesJson = null;
            } else {
              _tmpTextNotesJson = _cursor.getString(_cursorIndexOfTextNotesJson);
            }
            final String _tmpStickersJson;
            if (_cursor.isNull(_cursorIndexOfStickersJson)) {
              _tmpStickersJson = null;
            } else {
              _tmpStickersJson = _cursor.getString(_cursorIndexOfStickersJson);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final boolean _tmpHasContent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasContent);
            _tmpHasContent = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new PageEntity(_tmpId,_tmpNotebookId,_tmpTemplateId,_tmpOrder,_tmpTitle,_tmpDate,_tmpDrawingFilePath,_tmpTextNotesJson,_tmpStickersJson,_tmpThumbnailPath,_tmpHasContent,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<PageEntity> observeById(final String id) {
    final String _sql = "SELECT * FROM pages WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pages"}, new Callable<PageEntity>() {
      @Override
      @Nullable
      public PageEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNotebookId = CursorUtil.getColumnIndexOrThrow(_cursor, "notebookId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "order");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDrawingFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "drawingFilePath");
          final int _cursorIndexOfTextNotesJson = CursorUtil.getColumnIndexOrThrow(_cursor, "textNotesJson");
          final int _cursorIndexOfStickersJson = CursorUtil.getColumnIndexOrThrow(_cursor, "stickersJson");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfHasContent = CursorUtil.getColumnIndexOrThrow(_cursor, "hasContent");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final PageEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNotebookId;
            _tmpNotebookId = _cursor.getString(_cursorIndexOfNotebookId);
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final int _tmpOrder;
            _tmpOrder = _cursor.getInt(_cursorIndexOfOrder);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final Long _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            }
            final String _tmpDrawingFilePath;
            if (_cursor.isNull(_cursorIndexOfDrawingFilePath)) {
              _tmpDrawingFilePath = null;
            } else {
              _tmpDrawingFilePath = _cursor.getString(_cursorIndexOfDrawingFilePath);
            }
            final String _tmpTextNotesJson;
            if (_cursor.isNull(_cursorIndexOfTextNotesJson)) {
              _tmpTextNotesJson = null;
            } else {
              _tmpTextNotesJson = _cursor.getString(_cursorIndexOfTextNotesJson);
            }
            final String _tmpStickersJson;
            if (_cursor.isNull(_cursorIndexOfStickersJson)) {
              _tmpStickersJson = null;
            } else {
              _tmpStickersJson = _cursor.getString(_cursorIndexOfStickersJson);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final boolean _tmpHasContent;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfHasContent);
            _tmpHasContent = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new PageEntity(_tmpId,_tmpNotebookId,_tmpTemplateId,_tmpOrder,_tmpTitle,_tmpDate,_tmpDrawingFilePath,_tmpTextNotesJson,_tmpStickersJson,_tmpThumbnailPath,_tmpHasContent,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object countByNotebook(final String notebookId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM pages WHERE notebookId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, notebookId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
