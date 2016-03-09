package com.jsoh.myfirstandroidapp.notepad.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB를 사용하게 해 주는 헬퍼
 */
public class MemoDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memoDb.db";
    private static final int DATABASE_VERSION = 1;

    public MemoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초에 db가 열리는 타이밍에 table 생성
        db.execSQL(MemoContract.MemoEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
