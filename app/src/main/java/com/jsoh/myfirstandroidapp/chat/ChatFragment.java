package com.jsoh.myfirstandroidapp.chat;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.chat.client.ChatClient;
import com.jsoh.myfirstandroidapp.chat.client.MsgInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatFragment extends Fragment implements View.OnClickListener {
    private TextView mMessageListTextView;
    private EditText mMessageEdit;
    private ChatClient mChatClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessageListTextView = (TextView) view.findViewById(R.id.msg_list_text);
        mMessageEdit = (EditText) view.findViewById(R.id.edit_message);

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
//                Toast.makeText(getActivity(), msgInfo.toString(), Toast.LENGTH_SHORT).show();
                mMessageListTextView.setText(mMessageListTextView.getText().toString() + "\n" + msgInfo.getNickName() + ": " + msgInfo.getMessage());
                mMessageEdit.setText("");
            }
        });
    }
}
