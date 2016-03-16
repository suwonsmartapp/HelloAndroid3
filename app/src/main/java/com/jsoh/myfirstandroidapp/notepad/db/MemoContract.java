package com.jsoh.myfirstandroidapp.notepad.db;

import android.provider.BaseColumns;

/**
 * 스키마
 */
public class MemoContract {
    public static abstract class MemoEntry implements BaseColumns {
        public static final String TABLE_NAME = "Memo";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_MEMO = "memo";
        public static final String COLUMN_NAME_DATE = "date";

        // CREATE TABLE Memo (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT DEFAULT '제목없음', memo TEXT, date TEXT NOT NULL);
        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MemoEntry.TABLE_NAME + " (" +
                MemoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MemoEntry.COLUMN_NAME_TITLE + " TEXT DEFAULT '제목없음', " +
                MemoEntry.COLUMN_NAME_MEMO + " TEXT, " +
                MemoEntry.COLUMN_NAME_DATE + " TEXT DEFAULT CURRENT_TIMESTAMP" +
                ");";
    }

}
