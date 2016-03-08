package com.jsoh.myfirstandroidapp.notepad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.models.Memo;

import java.util.List;

/**
 * Created by junsuk on 16. 3. 8..
 */
public class MemoAdapter extends BaseAdapter {
    private final List<Memo> mData;
    private final LayoutInflater mInflator;

    public MemoAdapter(Context context, List<Memo> data) {
        mData = data;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.item_memo, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date_text);
            holder.title = (TextView) convertView.findViewById(R.id.title_text);
            holder.memo = (TextView) convertView.findViewById(R.id.memo_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Memo memo = (Memo) getItem(position);

        holder.date.setText(memo.getDate());
        holder.title.setText(memo.getTitle());
        holder.memo.setText(memo.getMemo());

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView memo;
        TextView date;
    }
}
