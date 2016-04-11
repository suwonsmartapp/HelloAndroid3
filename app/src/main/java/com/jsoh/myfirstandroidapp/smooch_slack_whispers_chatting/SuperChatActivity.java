package com.jsoh.myfirstandroidapp.smooch_slack_whispers_chatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.smooch.ui.ConversationActivity;

public class SuperChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConversationActivity.show(this);
    }
}
