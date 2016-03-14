package com.jsoh.myfirstandroidapp.notepad.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.Arrays;

/**
 * Created by junsuk on 16. 3. 8..
 */
public class MemoListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {

    private static final String TAG = MemoListFragment.class.getSimpleName();
    private MemoCursorAdapter mAdapter;
    private MemoFacade mFacade;
    private ListView mListView;
    private boolean mMultiChecked;
    private boolean[] mIsCheckedList;
    private int mSelectionCount = 0;

    private MemoFacade mMemoFacade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setTitle("메모 리스트");
        mListView = (ListView) view.findViewById(R.id.list);
        mFacade = new MemoFacade(getActivity());
        mAdapter = new MemoCursorAdapter(getContext(), null) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                if (mIsCheckedList != null && mIsCheckedList[cursor.getPosition()]) {
                    view.setBackgroundColor(Color.BLUE);
                } else {
                    view.setBackgroundColor(Color.WHITE);
                }
            }
        };

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

        // fragment에서의 back key 처리
        // http://stackoverflow.com/questions/7992216/android-fragment-handle-back-button-press
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);

        mMemoFacade = new MemoFacade(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        // BaseAdapter 에서의 데이터 변경 후 notifyDataSetChanged 와 동일
        mAdapter.swapCursor(mFacade.queryAllMemos());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mMultiChecked == false) {
            Cursor cursor = (Cursor) (parent.getAdapter()).getItem(position);
            Memo memo = Memo.cursorToMemo(cursor);
            Intent intent = new Intent(getActivity(), MemoEditActivity.class);
            intent.putExtra(MemoContract.MemoEntry._ID, cursor.getLong(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry._ID)));
            intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memo.getTitle());
            intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_MEMO, memo.getMemo());
            startActivity(intent);
        } else {
            Cursor cursor = (Cursor) (parent.getAdapter()).getItem(position);
            int mPosition = cursor.getPosition();

            if (mIsCheckedList[mPosition] == true) {
                mIsCheckedList[mPosition] = false;
                mSelectionCount--;
            } else {
                mIsCheckedList[mPosition] = true;
                mSelectionCount++;
            }
            setTitle("" + mSelectionCount);

            // 멀티체크 모드 벗어나기
            if (mSelectionCount < 1) {
                mMultiChecked = false;
                setTitle("메모 리스트");
                setHasOptionsMenu(false);
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
        Cursor cursor = (Cursor) (parent.getAdapter()).getItem(position);
        mIsCheckedList = new boolean[cursor.getCount()];
        mMultiChecked = true;

        // 현재 롱클릭 한 아이템을 선택 하고 다시 그리기
        mIsCheckedList[position] = true;
        mAdapter.notifyDataSetChanged();
        // 선택 된 갯수 초기화
        mSelectionCount = 1;
        // Title 변경
        setTitle("" + mSelectionCount);

        // 옵션 메뉴 설정
        setHasOptionsMenu(true);
        return true;
    }

    private void setTitle(String title) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_main, menu);

        // SearchView
        // https://pluu.github.io/blog/android/2015/05/19/android-toolbar-searchview/
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange : " + newText);

//                String selection2 = "title LIKE '%"++"%' OR memo LIKE %?%"
                String selection = "title LIKE '%" + newText + "%' OR memo LIKE '%" + newText + "%'";
                String[] selectionArgs = new String[] {
                        MemoContract.MemoEntry.COLUMN_NAME_TITLE,
                        MemoContract.MemoEntry.COLUMN_NAME_MEMO
                        };
                Cursor cursor = mMemoFacade.queryMemos(null,
                        selection,
                        null,
                        null,
                        null,
                        null);
                mAdapter.swapCursor(cursor);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (mMultiChecked) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                mMultiChecked = false;
                setTitle("메모 리스트");
                setHasOptionsMenu(false);

                // 모두 false로 셋팅
                Arrays.fill(mIsCheckedList, false);

                mAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }
}
