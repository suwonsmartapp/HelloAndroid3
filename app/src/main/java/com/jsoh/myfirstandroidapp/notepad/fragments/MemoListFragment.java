package com.jsoh.myfirstandroidapp.notepad.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.adapters.MemoCursorAdapter;
import com.jsoh.myfirstandroidapp.notepad.facade.MemoFacade;

/**
 * Created by junsuk on 16. 3. 8..
 */
public class MemoListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("메모 리스트");

        ListView listView = (ListView) view.findViewById(R.id.list);

        MemoFacade facade = new MemoFacade(getActivity());

        MemoCursorAdapter adapter = new MemoCursorAdapter(getActivity(), facade.queryAllMemos());

        listView.setAdapter(adapter);
    }
}