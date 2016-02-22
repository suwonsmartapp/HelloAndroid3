
package com.jsoh.myfirstandroidapp.exam_fragment;

import com.jsoh.myfirstandroidapp.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FragmentExamActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_exam);

        ColorFragment frag1 = (ColorFragment) getSupportFragmentManager().findFragmentById(
                R.id.frag1);

        frag1.setColor(Color.BLUE);

        Button button = (Button) findViewById(R.id.add_frag_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 랜덤한 색상
        int randomColor = Color.rgb((int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256));

        // 프래그먼트를 코드로 추가
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contents, ColorFragment.newInstance(randomColor))
                .commit();
    }
}
