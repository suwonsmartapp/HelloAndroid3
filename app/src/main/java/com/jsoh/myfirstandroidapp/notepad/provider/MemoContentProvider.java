package com.jsoh.myfirstandroidapp.notepad.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.db.MemoDbHelper;

public class MemoContentProvider extends ContentProvider {

    // 프로바이더 이름
    private static final String AUTHORITY = "com.jsoh.myfirstandroidapp.provider";

    // content://com.jsoh.myfirstandroidapp.provider/Memo
    // com.jsoh.myfirstandroidapp.provider 프로바이더의 Memo 테이블
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + MemoContract.MemoEntry.TABLE_NAME);

    // 1개의 아이템 요청 MIME type
    public static final String CONTENT_TYPE = "vnd.android.cursor.item/vnd.com.jsoh.myfirstandroidapp.provider." + MemoContract.MemoEntry.TABLE_NAME;
    // 여러개의 아이템 요청 MIME type
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.dir/vnd.com.jsoh.myfirstandroidapp.provider." + MemoContract.MemoEntry.TABLE_NAME;

    private static UriMatcher sUriMatcher;

    public static final int ALL = 1;
    public static final int ITEM = 2;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // content://com.jsoh.myfirstandroidapp.provider/Memo (1번 패턴)
        sUriMatcher.addURI(AUTHORITY, MemoContract.MemoEntry.TABLE_NAME, ALL);
        // content://com.jsoh.myfirstandroidapp.provider/Memo/#3 (2번 패턴)
        sUriMatcher.addURI(AUTHORITY, MemoContract.MemoEntry.TABLE_NAME + "/#", ITEM);
    }

    private MemoDbHelper mMemoDbHelper;

    public MemoContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case ALL:
                break;

            case ITEM:
                // uri의 #뒤의 숫자 (_id)만 뽑아서 조건문을 완성
                selection = "_id=" + ContentUris.parseId(uri);
                selectionArgs = null;
                break;

            case UriMatcher.NO_MATCH:
                return 0;
        }

        SQLiteDatabase db = mMemoDbHelper.getWritableDatabase();
        return db.delete(MemoContract.MemoEntry.TABLE_NAME,
                selection,
                selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // 이 프로바이더가 처리 할 수 있는 패턴인지 검사
        switch (sUriMatcher.match(uri)) {
            case ALL:
                return CONTENT_TYPE;
            case ITEM:
                return CONTENT_ITEM_TYPE;
        }
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case ALL:
                long id = mMemoDbHelper.getWritableDatabase().insert(MemoContract.MemoEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    // content://com.jsoh.myfirstandroidapp.provider/Memo#10
                    Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                    return returnUri;
                }
                break;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        mMemoDbHelper = new MemoDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case ALL:
                break;

            case ITEM:
                // uri의 #뒤의 숫자 (_id)만 뽑아서 조건문을 완성
                selection = "_id=" + ContentUris.parseId(uri);
                selectionArgs = null;
                break;
            case UriMatcher.NO_MATCH:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = mMemoDbHelper.getReadableDatabase();

        // select * from memo;
        return db.query(MemoContract.MemoEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case ALL:
                break;

            case ITEM:
                // uri의 #뒤의 숫자 (_id)만 뽑아서 조건문을 완성
                selection = "_id=" + ContentUris.parseId(uri);
                selectionArgs = null;
                break;

            case UriMatcher.NO_MATCH:
                return 0;
        }

        SQLiteDatabase db = mMemoDbHelper.getWritableDatabase();

        return db.update(MemoContract.MemoEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
