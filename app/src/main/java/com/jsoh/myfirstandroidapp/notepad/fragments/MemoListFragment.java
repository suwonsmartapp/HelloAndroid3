package com.jsoh.myfirstandroidapp.notepad.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.activities.MemoEditActivity;
import com.jsoh.myfirstandroidapp.notepad.adapters.MemoCursorAdapter;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.facade.MemoFacade;
import com.jsoh.myfirstandroidapp.notepad.models.Memo;

/**
 * Created by junsuk on 16. 3. 8..
 */
public class MemoListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private MemoCursorAdapter mAdapter;
    private MemoFacade mFacade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("메모 리스트");

        ListView listView = (ListView) view.findViewById(R.id.list);

        mFacade = new MemoFacade(getActivity());

        mAdapter = new MemoCursorAdapter(getActivity(), null);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        // BaseAdapter 에서의 데이터 변경 후 notifyDataSetChanged 와 동일
        mAdapter.swapCursor(mFacade.queryAllMemos());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) (parent.getAdapter()).getItem(position);

        Memo memo = Memo.cursorToMemo(cursor);

        Intent intent = new Intent(getActivity(), MemoEditActivity.class);
        intent.putExtra(MemoContract.MemoEntry._ID, cursor.getLong(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry._ID)));
        intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memo.getTitle());
        intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_MEMO, memo.getMemo());
        startActivity(intent);
    }
}
