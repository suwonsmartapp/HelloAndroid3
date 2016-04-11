package com.jsoh.myfirstandroidapp.smooch_slack_whispers_chatting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jsoh.myfirstandroidapp.R;

import io.smooch.core.Smooch;
import io.smooch.ui.ConversationActivity;

public class SuperChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_super_chat);

        findViewById(R.id.gcm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Smooch.track("gcm test");
            }
        });

        ConversationActivity.show(this);
    }
}
