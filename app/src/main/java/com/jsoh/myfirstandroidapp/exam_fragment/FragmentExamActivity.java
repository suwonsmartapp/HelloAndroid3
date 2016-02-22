
package com.jsoh.myfirstandroidapp.exam_fragment;

import com.jsoh.myfirstandroidapp.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FragmentExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_exam);

        ColorFragment frag1 = (ColorFragment) getSupportFragmentManager().findFragmentById(
                R.id.frag1);
        ColorFragment frag2 = (ColorFragment) getSupportFragmentManager().findFragmentById(
                R.id.frag2);

        frag1.setColor(Color.BLUE);
        frag2.setColor(Color.YELLOW);
    }
}
