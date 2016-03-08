package com.jsoh.myfirstandroidapp.notepad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.fragments.MemoEditFragment;

public class MemoEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contents, new MemoEditFragment())
                .commit();
    }
}
