package com.jsoh.myfirstandroidapp.notepad.fragments;

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

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.facade.MemoFacade;

public class MemoEditFragment extends Fragment {

    private EditText mTitle;
    private EditText mMemo;

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

        MemoFacade facade = new MemoFacade(getActivity());
        facade.insertMemo(mTitle.getText().toString(), mMemo.getText().toString());
    }
}
