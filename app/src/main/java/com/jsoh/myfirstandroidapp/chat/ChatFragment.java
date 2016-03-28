package com.jsoh.myfirstandroidapp.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.chat.client.ChatClient;
import com.jsoh.myfirstandroidapp.chat.client.MsgInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatFragment extends Fragment implements View.OnClickListener {
    private EditText mMessageEdit;
    private ChatClient mChatClient;
    private ListView mListView;
    private List<MsgInfo> mChatData;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessageEdit = (EditText) view.findViewById(R.id.edit_message);
        mListView = (ListView) view.findViewById(R.id.list_view);

        mChatData = new ArrayList<>();

        myAdapter = new MyAdapter(getActivity(), mChatData);
        mListView.setAdapter(myAdapter);


        view.findViewById(R.id.btn_send).setOnClickListener(this);

        // 서버에 접속
        new Thread(new Runnable() {
            @Override
            public void run() {
                mChatClient = new ChatClient();
                mChatClient.connect();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        mChatClient.sendMessage(mMessageEdit.getText().toString());
        mMessageEdit.setText("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mChatClient.close();
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    Handler mHandler = new Handler();

    @Subscribe
    @WorkerThread
    public void onMessage(final MsgInfo msgInfo) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mChatData.add(msgInfo);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    private static class MyAdapter extends BaseAdapter {
        private SimpleDateFormat mmSimpleDateFormat = new SimpleDateFormat("hh:dd a");

        private final LayoutInflater mmLayoutInflater;
        private final List<MsgInfo> mmData;

        public MyAdapter(Context context, List<MsgInfo> data) {
            mmLayoutInflater = LayoutInflater.from(context);
            mmData = data;
        }

        @Override
        public int getCount() {
            return mmData.size();
        }

        @Override
        public Object getItem(int position) {
            return mmData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                convertView = mmLayoutInflater.inflate(R.layout.item_chat_me, parent, false);
                holder.image = (ImageView) convertView.findViewById(R.id.image_you);
                holder.message_me = (TextView) convertView.findViewById(R.id.message_me);
                holder.message_you = (TextView) convertView.findViewById(R.id.message_you);
                holder.time_me = (TextView) convertView.findViewById(R.id.time_me);
                holder.time_you = (TextView) convertView.findViewById(R.id.time_you);
                holder.nickname = (TextView) convertView.findViewById(R.id.nickname_you);
                holder.layout_me = (LinearLayout) convertView.findViewById(R.id.layout_me);
                holder.layout_you = (LinearLayout) convertView.findViewById(R.id.layout_you);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MsgInfo msgInfo = (MsgInfo) getItem(position);
            if (msgInfo.getNickName().equals(ChatClient.NICKNAME)) {
                holder.message_me.setText(msgInfo.getMessage());
                holder.time_me.setText(mmSimpleDateFormat.format(new Date()));

                holder.layout_me.setVisibility(View.VISIBLE);
                holder.layout_you.setVisibility(View.GONE);
            } else {
                holder.message_you.setText(msgInfo.getMessage());
                holder.time_you.setText(mmSimpleDateFormat.format(new Date()));
                holder.nickname.setText(msgInfo.getNickName());
                holder.image.setImageResource(R.mipmap.ic_launcher);

                holder.layout_me.setVisibility(View.GONE);
                holder.layout_you.setVisibility(View.VISIBLE);

                if (position > 0 && msgInfo.getNickName().equals(((MsgInfo)getItem(position - 1)).getNickName())) {
                    holder.image.setVisibility(View.INVISIBLE);
                    holder.nickname.setVisibility(View.GONE);
                } else {
                    holder.image.setVisibility(View.VISIBLE);
                    holder.nickname.setVisibility(View.VISIBLE);
                }
            }

            return convertView;
        }

        private static class ViewHolder {
            LinearLayout layout_you;
            LinearLayout layout_me;
            TextView time_me;
            TextView time_you;
            TextView message_me;
            TextView message_you;
            TextView nickname;
            ImageView image;
        }
    }

}
