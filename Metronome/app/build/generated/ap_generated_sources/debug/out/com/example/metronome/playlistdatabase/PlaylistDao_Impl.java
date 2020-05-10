package com.example.metronome.playlistdatabase;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PlaylistDao_Impl implements PlaylistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Playlist> __insertionAdapterOfPlaylist;

  private final EntityDeletionOrUpdateAdapter<Playlist> __deletionAdapterOfPlaylist;

  private final EntityDeletionOrUpdateAdapter<Playlist> __updateAdapterOfPlaylist;

  public PlaylistDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaylist = new EntityInsertionAdapter<Playlist>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `playlists` (`id`,`playlist_name`,`playlist_songs`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Playlist value) {
        stmt.bindLong(1, value.id);
        if (value.playlist == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.playlist);
        }
        final String _tmp;
        _tmp = ConvertList.someObjectListToString(value.playlistSongs);
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
      }
    };
    this.__deletionAdapterOfPlaylist = new EntityDeletionOrUpdateAdapter<Playlist>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `playlists` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Playlist value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfPlaylist = new EntityDeletionOrUpdateAdapter<Playlist>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `playlists` SET `id` = ?,`playlist_name` = ?,`playlist_songs` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Playlist value) {
        stmt.bindLong(1, value.id);
        if (value.playlist == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.playlist);
        }
        final String _tmp;
        _tmp = ConvertList.someObjectListToString(value.playlistSongs);
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        stmt.bindLong(4, value.id);
      }
    };
  }

  @Override
  public void insert(final Playlist playlist) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPlaylist.insert(playlist);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Playlist playlist) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPlaylist.handle(playlist);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Playlist playlist) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPlaylist.handle(playlist);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Playlist> getAllPlaylists() {
    final String _sql = "SELECT * FROM playlists ORDER BY playlist_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPlaylist = CursorUtil.getColumnIndexOrThrow(_cursor, "playlist_name");
      final int _cursorIndexOfPlaylistSongs = CursorUtil.getColumnIndexOrThrow(_cursor, "playlist_songs");
      final List<Playlist> _result = new ArrayList<Playlist>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Playlist _item;
        final String _tmpPlaylist;
        _tmpPlaylist = _cursor.getString(_cursorIndexOfPlaylist);
        _item = new Playlist(_tmpPlaylist);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfPlaylistSongs);
        _item.playlistSongs = ConvertList.stringToSomeObjectList(_tmp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
