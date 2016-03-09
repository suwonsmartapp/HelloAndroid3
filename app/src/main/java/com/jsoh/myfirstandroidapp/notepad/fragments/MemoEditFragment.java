package com.jsoh.myfirstandroidapp.notepad.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.db.MemoDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoEditFragment extends Fragment {

    private EditText mTitle;
    private EditText mMemo;

    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memo_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("메모");

        // 프레그먼트에서 OptionsMenu
        setHasOptionsMenu(true);

        mTitle = (EditText) view.findViewById(R.id.title_edit);
        mMemo = (EditText) view.findViewById(R.id.memo_edit);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_main, menu);
    }

    @Override
    public void onPause() {
        super.onPause();

        // insert
        MemoDbHelper dbHelper = new MemoDbHelper(getActivity());

        // 쓰기 모드로 db 저장소를 얻기
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String title = mTitle.getText().toString();
        String memo = mMemo.getText().toString();
        String date = mSimpleDateFormat.format(new Date());

        // INSERT INTO Memo(title, memo, date) VALUES ('title', 'memo', '2000-10-10');
        // db.execSQL("INSERT INTO Memo(title, memo, date) VALUES ('title', 'memo', '2000-10-10')");

        ContentValues values = new ContentValues();
        values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, memo);
        values.put(MemoContract.MemoEntry.COLUMN_NAME_DATE, date);

        if (db.insert(MemoContract.MemoEntry.TABLE_NAME,
                null,
                values) == -1) {
            Toast.makeText(
                    getActivity(), "에러!!!", Toast.LENGTH_SHORT).show();
        }
    }
}
