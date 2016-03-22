package com.jsoh.myfirstandroidapp.notepad.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by junsuk on 16. 3. 22..
 */
public class MemoRecyclerAdapter extends RecyclerView.Adapter<MemoRecyclerAdapter.Holder> {

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
