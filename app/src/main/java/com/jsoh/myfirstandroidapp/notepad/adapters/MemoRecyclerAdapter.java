package com.jsoh.myfirstandroidapp.notepad.adapters;

import android.database.Cursor;
import android.database.CursorJoiner;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.notepad.db.MemoContract;

/**
 * Created by junsuk on 16. 3. 22..
 */
public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.Holder> {

    private OnItemClickListener mListener;

    public Cursor getItem(int position) {
        Cursor cursor = mCursor;
        cursor.moveToPosition(position);
        return cursor;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private Cursor mCursor;

    private Animation mAnimation;

    public MemoRecyclerAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
        final Holder holder = new Holder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onItemClick(itemView, holder.getAdapterPosition());
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mListener != null) {
                    mListener.onItemLongClick(itemView, holder.getAdapterPosition());
                }
                return true;
            }
        });

        if (mAnimation == null) {
            mAnimation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.translation);
        }
        return holder;
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

        if (mAnimation != null) {
            holder.itemView.startAnimation(mAnimation);
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
//        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        Cursor cursor = getItem(position);
        return cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
    }

    public void swapCursor(Cursor data) {
        Cursor oldCursor = mCursor;
        if (oldCursor != null && data != null && !oldCursor.isClosed() && !data.isClosed()) {
            String[] columns = {BaseColumns._ID};
            CursorJoiner joiner = new CursorJoiner(oldCursor, columns, data, columns);
            for (CursorJoiner.Result result : joiner) {
                switch (result) {
                    case LEFT:
                        notifyItemRemoved(data.getPosition());
                        Log.d("swapCursor", "LEFT");
                        break;
                    case RIGHT:
                        notifyItemInserted(data.getPosition());
                        Log.d("swapCursor", "RIGHT");
                        break;
                    case BOTH:
                        if (oldCursor.hashCode() != data.hashCode()) {
                            notifyItemChanged(data.getPosition());
                            Log.d("swapCursor", "BOTH");
                        }
                        break;
                }
            }
            oldCursor.close();
        }

        mCursor = data;
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
