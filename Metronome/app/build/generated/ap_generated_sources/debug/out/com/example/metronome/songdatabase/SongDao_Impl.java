package com.example.metronome.songdatabase;

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
public final class SongDao_Impl implements SongDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Song> __insertionAdapterOfSong;

  private final EntityDeletionOrUpdateAdapter<Song> __deletionAdapterOfSong;

  private final EntityDeletionOrUpdateAdapter<Song> __updateAdapterOfSong;

  public SongDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSong = new EntityInsertionAdapter<Song>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `songs` (`id`,`tempo`,`song_name`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Song value) {
        stmt.bindLong(1, value.id);
        if (value.getBpm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBpm());
        }
        if (value.getSongName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSongName());
        }
      }
    };
    this.__deletionAdapterOfSong = new EntityDeletionOrUpdateAdapter<Song>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `songs` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Song value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfSong = new EntityDeletionOrUpdateAdapter<Song>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `songs` SET `id` = ?,`tempo` = ?,`song_name` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Song value) {
        stmt.bindLong(1, value.id);
        if (value.getBpm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBpm());
        }
        if (value.getSongName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSongName());
        }
        stmt.bindLong(4, value.id);
      }
    };
  }

  @Override
  public void insert(final Song song) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSong.insert(song);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Song song) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSong.handle(song);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Song song) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSong.handle(song);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Song> getAllSongs() {
    final String _sql = "SELECT * FROM songs ORDER BY song_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBpm = CursorUtil.getColumnIndexOrThrow(_cursor, "tempo");
      final int _cursorIndexOfSongName = CursorUtil.getColumnIndexOrThrow(_cursor, "song_name");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        final String _tmpBpm;
        _tmpBpm = _cursor.getString(_cursorIndexOfBpm);
        final String _tmpSongName;
        _tmpSongName = _cursor.getString(_cursorIndexOfSongName);
        _item = new Song(_tmpBpm,_tmpSongName);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
