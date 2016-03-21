package com.jsoh.myfirstandroidapp.notepad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.fragments.MemoEditFragment;

public class MemoEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        Fragment fragment = null;

        // 내가 호출 된 Intent 의 내용을 확인
        Intent intent = getIntent();
        String extra_text = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (intent != null) {
            if (extra_text != null) {
                // 외부에서 호출 됨
                fragment = MemoEditFragment.newInstance(extra_text);
            } else {
                // 내부 처리
                long id = intent.getLongExtra(MemoContract.MemoEntry._ID, -1);

                if (id != -1) {
                    String title = intent.getStringExtra(MemoContract.MemoEntry.COLUMN_NAME_TITLE);
                    String memo = intent.getStringExtra(MemoContract.MemoEntry.COLUMN_NAME_MEMO);
                    String image = intent.getStringExtra(MemoContract.MemoEntry.COLUMN_NAME_IMAGE);

                    fragment = MemoEditFragment.newInstance(id, title, memo, image);
                } else {
                    fragment = new MemoEditFragment();
                }
            }
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contents, fragment)
                .commit();
    }
}
