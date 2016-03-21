package com.jsoh.myfirstandroidapp.notepad.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.activities.MemoEditActivity;
import com.jsoh.myfirstandroidapp.notepad.adapters.MemoCursorAdapter;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.models.Memo;
import com.jsoh.myfirstandroidapp.notepad.provider.MemoContentProvider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by junsuk on 16. 3. 8..
 */
public class MemoListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnKeyListener {

    private static final String TAG = MemoListFragment.class.getSimpleName();
    private MemoCursorAdapter mAdapter;
    private ListView mListView;
    private boolean mMultiChecked;
    private Set<Integer> mIsCheckedSet = new HashSet<>();
    private int mSelectionCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_memo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setTitle("메모 리스트");
        mListView = (ListView) view.findViewById(R.id.list);
        mAdapter = new MemoCursorAdapter(getContext(), null) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
                // TODO 검토
                if (mIsCheckedSet != null && mIsCheckedSet.contains(cursor.getPosition())) {
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

        // Loader 시작
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                // Background Thread
                // MemoContentProvider 를 통해 query 를 수행한다
                return new CursorLoader(getActivity(), MemoContentProvider.CONTENT_URI,
                        null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                // UI Thread
                mAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });
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
            intent.putExtra(MemoContract.MemoEntry.COLUMN_NAME_IMAGE, cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_IMAGE)));
            startActivity(intent);
        } else {
            Cursor cursor = (Cursor) (parent.getAdapter()).getItem(position);

            if (mIsCheckedSet.contains(position)) {
                mIsCheckedSet.remove(position);
                mSelectionCount--;
            } else {
                mIsCheckedSet.add(position);
                mSelectionCount++;
            }
            setTitle("" + mSelectionCount);

            // 멀티체크 모드 벗어나기
            if (mSelectionCount < 1) {
                setMultiCheckMode(false);
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
        setMultiCheckMode(true);

        // 현재 롱클릭 한 아이템을 선택 하고 다시 그리기
        mIsCheckedSet.add(position);
        mAdapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.menu_note, menu);

        // SearchView
        // https://pluu.github.io/blog/android/2015/05/19/android-toolbar-searchview/
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            android.os.Handler handler = new android.os.Handler();
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                Log.d(TAG, "onQueryTextChange : " + newText);

//                String selection2 = "title LIKE '%"++"%' OR memo LIKE %?%"
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String selection = "title LIKE '%" + newText + "%' OR memo LIKE '%" + newText + "%'";
                        final Cursor cursor = getActivity().getContentResolver().query(MemoContentProvider.CONTENT_URI,
                                null,
                                selection,
                                null,
                                null);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.swapCursor(cursor);
                            }
                        });
                    }
                }).start();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            showDeleteDialog();
            return true;
        } else if (id == R.id.action_export) {

        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        String ids = "";
        Iterator<Integer> iterator = mIsCheckedSet.iterator();
        while (iterator.hasNext()) {
            int position = iterator.next();
            ids = ids + mAdapter.getItemId(position);;
            if (iterator.hasNext()) {
                ids += ",";
            }
        }
        Log.d(TAG, ids);

        final String finalIds = ids;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("메모 삭제")
                .setMessage("메모를 삭제하시겠습니까?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int deleted = getActivity().getContentResolver().delete(MemoContentProvider.CONTENT_URI,
                                "_id IN (" + finalIds + ")", null);
                        if (deleted > 0) {
                            Toast.makeText(getActivity(), "삭제 되었습니다", Toast.LENGTH_SHORT).show();

                            setMultiCheckMode(false);

                            mAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("아니오", null);
        builder.show();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (mMultiChecked) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                setMultiCheckMode(false);

                mAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    private void setMultiCheckMode(boolean isMultiCheckMode) {
        mMultiChecked = isMultiCheckMode;
        setHasOptionsMenu(isMultiCheckMode);
        mIsCheckedSet.clear();

        if (isMultiCheckMode) {
            // 선택 된 갯수 초기화
            mSelectionCount = 1;
            // Title 변경
            setTitle("" + mSelectionCount);
        } else {
            setTitle("메모 리스트");
        }
    }
}
