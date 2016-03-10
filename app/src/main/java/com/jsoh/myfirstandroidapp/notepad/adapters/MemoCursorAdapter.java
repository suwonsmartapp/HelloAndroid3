package com.jsoh.myfirstandroidapp.notepad.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;

/**
 * Created by junsuk on 16. 3. 10..
 */
public class MemoCursorAdapter extends CursorAdapter {
    private final LayoutInflater mInflator;

    public MemoCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = mInflator.inflate(R.layout.item_memo, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.title_text);
        holder.memo = (TextView) convertView.findViewById(R.id.memo_text);
        holder.date = (TextView) convertView.findViewById(R.id.date_text);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
        String memo = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_MEMO));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_DATE));

        holder.title.setText(title);
        holder.memo.setText(memo);
        holder.date.setText(date);
    }

    private static class ViewHolder {
        TextView title;
        TextView memo;
        TextView date;
    }
}
