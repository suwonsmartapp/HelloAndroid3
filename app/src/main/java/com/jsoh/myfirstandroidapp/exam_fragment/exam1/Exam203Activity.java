
package com.jsoh.myfirstandroidapp.exam_fragment.exam1;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.exam_fragment.ColorFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Exam203Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam203);
    }

    public void onClick(View view) {
        int fragmentId = -1;
        switch (view.getId()) {
            case R.id.button1:
                fragmentId = R.id.frag1;
                break;
            case R.id.button2:
                fragmentId = R.id.frag2;
                break;
            case R.id.button3:
                fragmentId = R.id.frag3;
                break;
        }

        addFragment(fragmentId);
    }

    private void addFragment(int fragmentId) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentId, ColorFragment.newInstance(
                        Color.rgb((int) (Math.random() * 256),
                                (int) (Math.random() * 256),
                                (int) (Math.random() * 256))
                        )).commit();
    }
}
