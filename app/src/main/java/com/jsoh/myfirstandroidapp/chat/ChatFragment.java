package com.jsoh.myfirstandroidapp.chat;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.chat.client.ChatClient;
import com.jsoh.myfirstandroidapp.chat.client.MsgInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ChatFragment extends Fragment implements View.OnClickListener {
    private EditText mMessageEdit;
    private ChatClient mChatClient;
    private LinearLayout mLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessageEdit = (EditText) view.findViewById(R.id.edit_message);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

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
                // <TextView />
                TextView textView = new TextView(getActivity());
                //  android:layout_width="wrap_content"
                //  android:layout_height="wrap_content"
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //  android:layout_gravity="right"

                if (msgInfo.getNickName().equals("오준석")) {
                    params.gravity = Gravity.RIGHT;
                } else {
                    params.gravity = Gravity.LEFT;
                }
                textView.setLayoutParams(params);
                //  android:background="@drawable/bubble"
                textView.setBackgroundResource(R.drawable.bubble);
                textView.setText(msgInfo.getNickName() + ": " + msgInfo.getMessage());

                mLinearLayout.addView(textView);
            }
        });
    }
}
