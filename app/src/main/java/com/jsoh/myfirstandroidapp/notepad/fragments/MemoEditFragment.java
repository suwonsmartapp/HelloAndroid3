package com.jsoh.myfirstandroidapp.notepad.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;
import com.jsoh.myfirstandroidapp.notepad.facade.MemoFacade;
import com.jsoh.myfirstandroidapp.notepad.provider.MemoContentProvider;

public class MemoEditFragment extends Fragment {

    private EditText mTitleTextView;
    private EditText mMemoTextView;

    private String mTitle = "";
    private String mMemo = "";

    private boolean isEditMode;
    private long mId = -1;
    private MemoFacade mMemoFacade;

    public MemoEditFragment() {
    }

    public static MemoEditFragment newInstance(long id, String title, String memo) {

        MemoEditFragment fragment = new MemoEditFragment();

        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("title", title);
        bundle.putString("memo", memo);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMemoFacade = new MemoFacade(getActivity());
        return inflater.inflate(R.layout.fragment_memo_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("메모");

        // 프레그먼트에서 OptionsMenu
        setHasOptionsMenu(true);

        mTitleTextView = (EditText) view.findViewById(R.id.title_edit);
        mMemoTextView = (EditText) view.findViewById(R.id.memo_edit);

        Bundle bundle = getArguments();
        if (bundle != null) {
            isEditMode = true;

            mId = bundle.getLong("id");
            String title = bundle.getString("title");
            String memo = bundle.getString("memo");

            mTitle = title;
            mMemo = memo;

            mTitleTextView.setText(title);
            mMemoTextView.setText(memo);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (isEditMode) {
            inflater.inflate(R.menu.note_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("메모 삭제")
                .setMessage("메모를 삭제하시겠습니까?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int deleted = getActivity().getContentResolver().delete(MemoContentProvider.CONTENT_URI,
                                "_id=" + mId, null);
                        if (deleted > 0) {
                            Toast.makeText(getActivity(), "삭제 되었습니다", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                })
                .setNegativeButton("아니오", null);
                builder.show();

                break;
            case R.id.action_export:
                // TODO 내보내기
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();



        String title = mTitleTextView.getText().toString();
        String memo = mMemoTextView.getText().toString();
        if (isEditMode) {
            // 수정모드
            if (!(mTitle.equals(title) && mMemo.equals(memo))) {
                ContentValues values = new ContentValues();
                values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
                values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, memo);
                int updated = getActivity().getContentResolver().update(MemoContentProvider.CONTENT_URI,
                        values, "_id=" + mId, null);

                if (updated > 0) {
                    Toast.makeText(getActivity(), "수정 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // 삽입모드

            ContentValues values = new ContentValues();
            if (title.length() != 0) {
                values.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
            }
            values.put(MemoContract.MemoEntry.COLUMN_NAME_MEMO, memo);
            Uri insertUri = getActivity().getContentResolver().insert(MemoContentProvider.CONTENT_URI,
                    values);
            if (insertUri != null) {
                Toast.makeText(getActivity(), "저장 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
