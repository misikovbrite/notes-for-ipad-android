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
public final class NotebookDao_Impl implements NotebookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NotebookEntity> __insertionAdapterOfNotebookEntity;

  private final EntityDeletionOrUpdateAdapter<NotebookEntity> __deletionAdapterOfNotebookEntity;

  private final EntityDeletionOrUpdateAdapter<NotebookEntity> __updateAdapterOfNotebookEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfSetFavorite;

  private final SharedSQLiteStatement __preparedStmtOfSetArchived;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePageCount;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastPage;

  public NotebookDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNotebookEntity = new EntityInsertionAdapter<NotebookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notebooks` (`id`,`name`,`colorHex`,`spineColorHex`,`iconName`,`coverId`,`templateId`,`templateColorThemeId`,`isFavorite`,`isArchived`,`pageCount`,`lastPageId`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NotebookEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getColorHex());
        statement.bindString(4, entity.getSpineColorHex());
        statement.bindString(5, entity.getIconName());
        if (entity.getCoverId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCoverId());
        }
        statement.bindString(7, entity.getTemplateId());
        statement.bindString(8, entity.getTemplateColorThemeId());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isArchived() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindLong(11, entity.getPageCount());
        if (entity.getLastPageId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getLastPageId());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfNotebookEntity = new EntityDeletionOrUpdateAdapter<NotebookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `notebooks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NotebookEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfNotebookEntity = new EntityDeletionOrUpdateAdapter<NotebookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `notebooks` SET `id` = ?,`name` = ?,`colorHex` = ?,`spineColorHex` = ?,`iconName` = ?,`coverId` = ?,`templateId` = ?,`templateColorThemeId` = ?,`isFavorite` = ?,`isArchived` = ?,`pageCount` = ?,`lastPageId` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NotebookEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getColorHex());
        statement.bindString(4, entity.getSpineColorHex());
        statement.bindString(5, entity.getIconName());
        if (entity.getCoverId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCoverId());
        }
        statement.bindString(7, entity.getTemplateId());
        statement.bindString(8, entity.getTemplateColorThemeId());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isArchived() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindLong(11, entity.getPageCount());
        if (entity.getLastPageId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getLastPageId());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        statement.bindString(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notebooks WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notebooks SET isFavorite = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetArchived = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notebooks SET isArchived = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePageCount = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notebooks SET pageCount = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastPage = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE notebooks SET lastPageId = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final NotebookEntity notebook,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNotebookEntity.insert(notebook);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final NotebookEntity notebook,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNotebookEntity.handle(notebook);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final NotebookEntity notebook,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNotebookEntity.handle(notebook);
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
  public Object setFavorite(final String id, final boolean isFavorite, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetFavorite.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
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
          __preparedStmtOfSetFavorite.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setArchived(final String id, final boolean isArchived, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetArchived.acquire();
        int _argIndex = 1;
        final int _tmp = isArchived ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
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
          __preparedStmtOfSetArchived.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePageCount(final String id, final int count, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePageCount.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, count);
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
          __preparedStmtOfUpdatePageCount.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastPage(final String id, final String pageId, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastPage.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, pageId);
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
          __preparedStmtOfUpdateLastPage.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NotebookEntity>> observeAll() {
    final String _sql = "SELECT * FROM notebooks WHERE isArchived = 0 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notebooks"}, new Callable<List<NotebookEntity>>() {
      @Override
      @NonNull
      public List<NotebookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfSpineColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "spineColorHex");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCoverId = CursorUtil.getColumnIndexOrThrow(_cursor, "coverId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfTemplateColorThemeId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateColorThemeId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
          final int _cursorIndexOfLastPageId = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPageId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<NotebookEntity> _result = new ArrayList<NotebookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NotebookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpSpineColorHex;
            _tmpSpineColorHex = _cursor.getString(_cursorIndexOfSpineColorHex);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpCoverId;
            if (_cursor.isNull(_cursorIndexOfCoverId)) {
              _tmpCoverId = null;
            } else {
              _tmpCoverId = _cursor.getString(_cursorIndexOfCoverId);
            }
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpTemplateColorThemeId;
            _tmpTemplateColorThemeId = _cursor.getString(_cursorIndexOfTemplateColorThemeId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final int _tmpPageCount;
            _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            final String _tmpLastPageId;
            if (_cursor.isNull(_cursorIndexOfLastPageId)) {
              _tmpLastPageId = null;
            } else {
              _tmpLastPageId = _cursor.getString(_cursorIndexOfLastPageId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new NotebookEntity(_tmpId,_tmpName,_tmpColorHex,_tmpSpineColorHex,_tmpIconName,_tmpCoverId,_tmpTemplateId,_tmpTemplateColorThemeId,_tmpIsFavorite,_tmpIsArchived,_tmpPageCount,_tmpLastPageId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<NotebookEntity>> observeFavorites() {
    final String _sql = "SELECT * FROM notebooks WHERE isFavorite = 1 AND isArchived = 0 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notebooks"}, new Callable<List<NotebookEntity>>() {
      @Override
      @NonNull
      public List<NotebookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfSpineColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "spineColorHex");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCoverId = CursorUtil.getColumnIndexOrThrow(_cursor, "coverId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfTemplateColorThemeId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateColorThemeId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
          final int _cursorIndexOfLastPageId = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPageId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<NotebookEntity> _result = new ArrayList<NotebookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NotebookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpSpineColorHex;
            _tmpSpineColorHex = _cursor.getString(_cursorIndexOfSpineColorHex);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpCoverId;
            if (_cursor.isNull(_cursorIndexOfCoverId)) {
              _tmpCoverId = null;
            } else {
              _tmpCoverId = _cursor.getString(_cursorIndexOfCoverId);
            }
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpTemplateColorThemeId;
            _tmpTemplateColorThemeId = _cursor.getString(_cursorIndexOfTemplateColorThemeId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final int _tmpPageCount;
            _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            final String _tmpLastPageId;
            if (_cursor.isNull(_cursorIndexOfLastPageId)) {
              _tmpLastPageId = null;
            } else {
              _tmpLastPageId = _cursor.getString(_cursorIndexOfLastPageId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new NotebookEntity(_tmpId,_tmpName,_tmpColorHex,_tmpSpineColorHex,_tmpIconName,_tmpCoverId,_tmpTemplateId,_tmpTemplateColorThemeId,_tmpIsFavorite,_tmpIsArchived,_tmpPageCount,_tmpLastPageId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<NotebookEntity>> observeArchived() {
    final String _sql = "SELECT * FROM notebooks WHERE isArchived = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notebooks"}, new Callable<List<NotebookEntity>>() {
      @Override
      @NonNull
      public List<NotebookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfSpineColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "spineColorHex");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCoverId = CursorUtil.getColumnIndexOrThrow(_cursor, "coverId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfTemplateColorThemeId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateColorThemeId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
          final int _cursorIndexOfLastPageId = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPageId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<NotebookEntity> _result = new ArrayList<NotebookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NotebookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpSpineColorHex;
            _tmpSpineColorHex = _cursor.getString(_cursorIndexOfSpineColorHex);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpCoverId;
            if (_cursor.isNull(_cursorIndexOfCoverId)) {
              _tmpCoverId = null;
            } else {
              _tmpCoverId = _cursor.getString(_cursorIndexOfCoverId);
            }
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpTemplateColorThemeId;
            _tmpTemplateColorThemeId = _cursor.getString(_cursorIndexOfTemplateColorThemeId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final int _tmpPageCount;
            _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            final String _tmpLastPageId;
            if (_cursor.isNull(_cursorIndexOfLastPageId)) {
              _tmpLastPageId = null;
            } else {
              _tmpLastPageId = _cursor.getString(_cursorIndexOfLastPageId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new NotebookEntity(_tmpId,_tmpName,_tmpColorHex,_tmpSpineColorHex,_tmpIconName,_tmpCoverId,_tmpTemplateId,_tmpTemplateColorThemeId,_tmpIsFavorite,_tmpIsArchived,_tmpPageCount,_tmpLastPageId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getById(final String id, final Continuation<? super NotebookEntity> $completion) {
    final String _sql = "SELECT * FROM notebooks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NotebookEntity>() {
      @Override
      @Nullable
      public NotebookEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfSpineColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "spineColorHex");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCoverId = CursorUtil.getColumnIndexOrThrow(_cursor, "coverId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfTemplateColorThemeId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateColorThemeId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
          final int _cursorIndexOfLastPageId = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPageId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final NotebookEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpSpineColorHex;
            _tmpSpineColorHex = _cursor.getString(_cursorIndexOfSpineColorHex);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpCoverId;
            if (_cursor.isNull(_cursorIndexOfCoverId)) {
              _tmpCoverId = null;
            } else {
              _tmpCoverId = _cursor.getString(_cursorIndexOfCoverId);
            }
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpTemplateColorThemeId;
            _tmpTemplateColorThemeId = _cursor.getString(_cursorIndexOfTemplateColorThemeId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final int _tmpPageCount;
            _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            final String _tmpLastPageId;
            if (_cursor.isNull(_cursorIndexOfLastPageId)) {
              _tmpLastPageId = null;
            } else {
              _tmpLastPageId = _cursor.getString(_cursorIndexOfLastPageId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new NotebookEntity(_tmpId,_tmpName,_tmpColorHex,_tmpSpineColorHex,_tmpIconName,_tmpCoverId,_tmpTemplateId,_tmpTemplateColorThemeId,_tmpIsFavorite,_tmpIsArchived,_tmpPageCount,_tmpLastPageId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<NotebookEntity> observeById(final String id) {
    final String _sql = "SELECT * FROM notebooks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notebooks"}, new Callable<NotebookEntity>() {
      @Override
      @Nullable
      public NotebookEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfSpineColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "spineColorHex");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfCoverId = CursorUtil.getColumnIndexOrThrow(_cursor, "coverId");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateId");
          final int _cursorIndexOfTemplateColorThemeId = CursorUtil.getColumnIndexOrThrow(_cursor, "templateColorThemeId");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfPageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pageCount");
          final int _cursorIndexOfLastPageId = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPageId");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final NotebookEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final String _tmpSpineColorHex;
            _tmpSpineColorHex = _cursor.getString(_cursorIndexOfSpineColorHex);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final String _tmpCoverId;
            if (_cursor.isNull(_cursorIndexOfCoverId)) {
              _tmpCoverId = null;
            } else {
              _tmpCoverId = _cursor.getString(_cursorIndexOfCoverId);
            }
            final String _tmpTemplateId;
            _tmpTemplateId = _cursor.getString(_cursorIndexOfTemplateId);
            final String _tmpTemplateColorThemeId;
            _tmpTemplateColorThemeId = _cursor.getString(_cursorIndexOfTemplateColorThemeId);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final boolean _tmpIsArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_1 != 0;
            final int _tmpPageCount;
            _tmpPageCount = _cursor.getInt(_cursorIndexOfPageCount);
            final String _tmpLastPageId;
            if (_cursor.isNull(_cursorIndexOfLastPageId)) {
              _tmpLastPageId = null;
            } else {
              _tmpLastPageId = _cursor.getString(_cursorIndexOfLastPageId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new NotebookEntity(_tmpId,_tmpName,_tmpColorHex,_tmpSpineColorHex,_tmpIconName,_tmpCoverId,_tmpTemplateId,_tmpTemplateColorThemeId,_tmpIsFavorite,_tmpIsArchived,_tmpPageCount,_tmpLastPageId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object countActive(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM notebooks WHERE isArchived = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
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
