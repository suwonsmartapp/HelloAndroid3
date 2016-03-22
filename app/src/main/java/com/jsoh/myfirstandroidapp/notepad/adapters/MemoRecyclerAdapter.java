package com.jsoh.myfirstandroidapp.notepad.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;

/**
 * Created by junsuk on 16. 3. 22..
 */
public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.Holder> {

    private final Cursor mCursor;

    public MemoRecyclerAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Cursor cursor = mCursor;
        cursor.moveToPosition(position);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
        String memo = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_MEMO));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_DATE));

        holder.title.setText(title);
        holder.memo.setText(memo);
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
//        return mCursor == null ? 0 : mCursor.getCount();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView memo;
        TextView date;

        public Holder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_text);
            memo = (TextView) itemView.findViewById(R.id.memo_text);
            date = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
