package com.jsoh.myfirstandroidapp.notepad.facade;

import android.content.ContentValues;
import android.database.sqlite.SQLiteStatement;

import com.jsoh.myfirstandroidapp.BuildConfig;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.db.MemoDbHelper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, sdk = 21)
@RunWith(RobolectricGradleTestRunner.class)
public class DbTest {

    private MemoDbHelper mHelper;

    @Before
    public void setUp() throws Exception {
        mHelper = new MemoDbHelper(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void 기존방법insert1개() throws Exception {
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, "타이틀");
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, "메모");

        long id = mHelper.getWritableDatabase().insert(MemoContract.MemoEntry.TABLE_NAME, null, values);
        Assert.assertNotEquals(-1, id);
    }

    @Test
    public void 기존방법insert10000개() throws Exception {
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, "타이틀");
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, "메모");

        for (int i = 0; i < 10000; i++) {
            long id = mHelper.getWritableDatabase().insert(MemoContract.MemoEntry.TABLE_NAME, null, values);
            Assert.assertNotEquals(-1, id);
        }
    }

    @Test
    public void Transaction처리insert10000개() throws Exception {
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, "타이틀");
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, "메모");

        try {
            // 트랜잭션 시작
            mHelper.getWritableDatabase().beginTransaction();

            for (int i = 0; i < 10000; i++) {
                long id = mHelper.getWritableDatabase().insert(MemoContract.MemoEntry.TABLE_NAME, null, values);
                Assert.assertNotEquals(-1, id);
            }

            // 정상 종료
            mHelper.getWritableDatabase().setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            // 트랜잭션 종료
            mHelper.getWritableDatabase().endTransaction();
        }
    }

    @Test
    public void SQLiteStatement처리insert1개() throws Exception {
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, "타이틀");
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, "메모");

        SQLiteStatement stmt = mHelper.getWritableDatabase().compileStatement("INSERT INTO Memo (title, memo) VALUES (?, ?)");
        stmt.bindString(1, "타이틀");
        stmt.bindString(2, "메모");
        long id = stmt.executeInsert();
        Assert.assertNotEquals(-1, id);
    }

    @Test
    public void SQLiteStatement처리insert10000개() throws Exception {
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, "타이틀");
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, "메모");

        SQLiteStatement stmt = mHelper.getWritableDatabase().compileStatement("INSERT INTO Memo (title, memo) VALUES (?, ?)");
        for (int i = 0; i < 10000; i++) {
            stmt.bindString(1, "타이틀");
            stmt.bindString(2, "메모");
            long id = stmt.executeInsert();
            Assert.assertNotEquals(-1, id);
        }
    }

    @Test
    public void SQLiteStatement트랜잭션처리insert10000개() throws Exception {
        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, "타이틀");
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, "메모");

        try {
            mHelper.getWritableDatabase().beginTransaction();

            SQLiteStatement stmt = mHelper.getWritableDatabase().compileStatement("INSERT INTO Memo (title, memo) VALUES (?, ?)");
            for (int i = 0; i < 10000; i++) {
                stmt.bindString(1, "타이틀");
                stmt.bindString(2, "메모");
                long id = stmt.executeInsert();
                Assert.assertNotEquals(-1, id);
            }

            mHelper.getWritableDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mHelper.getWritableDatabase().endTransaction();
        }
    }
}
