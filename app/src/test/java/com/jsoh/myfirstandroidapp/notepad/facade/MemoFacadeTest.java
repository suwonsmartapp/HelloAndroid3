package com.jsoh.myfirstandroidapp.notepad.facade;

import android.content.ContentValues;
import android.database.Cursor;

import com.jsoh.myfirstandroidapp.BuildConfig;

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
public class MemoFacadeTest {
    MemoFacade mMemoFacade;

    @Before
    public void setUp() throws Exception {
        mMemoFacade = new MemoFacade(RuntimeEnvironment.application);

        mMemoFacade.insertMemo("test", "test");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInsertMemo() throws Exception {
        long id = mMemoFacade.insertMemo("test", "test");
        Assert.assertEquals(2, id);

        id = mMemoFacade.insertMemo("test", "test");
        Assert.assertEquals(3, id);
    }

    @Test
    public void testQueryAllMemos() throws Exception {
        Assert.assertEquals(1, mMemoFacade.queryAllMemos().getCount());
    }

    @Test
    public void testQueryMemos() throws Exception {
        mMemoFacade.insertMemo("test", "test");

        Cursor cursor = mMemoFacade.queryAllMemos();
        Assert.assertEquals(2, cursor.getCount());
    }

    @Test
    public void testUpdateMemo() throws Exception {
        ContentValues values = new ContentValues();
        values.put("title", "test2");

        int row = mMemoFacade.updateMemo(values,
                "title='test'", null);
        Assert.assertEquals(1, row);

    }

    @Test
    public void testDeleteMemo() throws Exception {

    }
}